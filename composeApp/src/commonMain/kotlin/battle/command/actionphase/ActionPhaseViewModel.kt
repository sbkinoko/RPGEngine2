package battle.command.actionphase

import battle.BattleChildViewModel
import battle.QualifierAttackFromEnemy
import battle.QualifierAttackFromPlayer
import battle.domain.ActionData
import battle.domain.ActionType
import battle.domain.AttackPhaseCommand
import battle.domain.AttackSkill
import battle.domain.CommandType
import battle.domain.FinishCommand
import battle.domain.HealSkill
import battle.repository.action.ActionRepository
import battle.repository.battlemonster.BattleMonsterRepository
import battle.usecase.IsAllMonsterNotActiveUseCase
import battle.usecase.attack.AttackUseCase
import battle.usecase.findactivetarget.FindActiveTargetUseCase
import battle.usecase.updateparameter.UpdateMonsterStatusUseCase
import battle.usecase.updateparameter.UpdatePlayerStatusUseCase
import battle.usecase.updateparameter.UpdateStatusService
import common.values.playerNum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import main.repository.player.PlayerRepository
import main.repository.skill.ATTACK_NORMAL
import main.repository.skill.SkillRepository
import main.status.Status
import menu.domain.SelectManager
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ActionPhaseViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()
    private val playerRepository: PlayerRepository by inject()
    private val skillRepository: SkillRepository by inject()

    private val updatePlayerParameter: UpdatePlayerStatusUseCase by inject()
    private val updateEnemyParameter: UpdateMonsterStatusUseCase by inject()

    private val attackFromPlayerUseCase: AttackUseCase by inject(
        qualifier = named(QualifierAttackFromPlayer)
    )
    private val attackFromEnemyUseCase: AttackUseCase by inject(
        qualifier = named(QualifierAttackFromEnemy)
    )
    private val findActiveTargetUseCase: FindActiveTargetUseCase by inject()
    private val isAllMonsterNotActiveUseCase: IsAllMonsterNotActiveUseCase by inject()

    override val canBack: Boolean
        get() = false

    init {
        CoroutineScope(Dispatchers.IO).launch {
            commandStateRepository.commandTypeFlow.collect {
                if (it is AttackPhaseCommand) {
                    mutableAttackingPlayerId.value = 0
                }
            }
        }
    }

    // fixme attackingPlayerは削除する
    // 敵の攻撃が挟まってPlayerだけじゃなくなるから
    private val mutableAttackingPlayerId: MutableStateFlow<Int> = MutableStateFlow(0)
    val attackingPlayerId: StateFlow<Int> = mutableAttackingPlayerId.asStateFlow()

    // 使わないので適当
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    fun getActionText(playerId: Int): String {
        val actionStatusName = getActionCharacterName(playerId)
        val forStatusName = getForStatusName(playerId)

        val actionStatusActionName = getActionName(playerId)
        return actionStatusName + "の" +
                forStatusName + "への" +
                actionStatusActionName
    }

    private fun getActionCharacterName(id: Int): String {
        return if (id < playerNum) {
            playerRepository.getStatus(id).name
        } else {
            battleMonsterRepository.getStatus(id - playerNum).name
        }
    }

    private enum class Type {
        ATTACK,
        HEAL,
    }

    private fun getForStatusName(id: Int): String {
        return if (id < playerNum) {
            val action = actionRepository.getAction(id)

            val type = when (action.thisTurnAction) {
                ActionType.Normal -> Type.ATTACK
                ActionType.Skill -> when (skillRepository.getSkill(action.skillId!!)) {
                    is AttackSkill -> Type.ATTACK
                    is HealSkill -> Type.HEAL
                }

                ActionType.None -> throw RuntimeException("ここには来ない")
            }

            when (type) {
                Type.ATTACK -> {
                    val targetId = action.target
                    battleMonsterRepository.getStatus(targetId).name
                }

                Type.HEAL -> {
                    val targetId = action.ally
                    playerRepository.getStatus(targetId).name
                }
            }
        } else {
            //　todo 敵の攻撃対象を保存するようにしたら修正
            "仲間"
        }
    }

    private fun getActionName(id: Int): String {

        val action = if (id < playerNum) {
            actionRepository.getAction(id).thisTurnAction
        } else {
            ActionType.Skill
        }
        return when (action) {
            ActionType.Normal -> "攻撃"
            ActionType.Skill -> {
                val skill = if (id < playerNum) {
                    val skillId = actionRepository.getAction(id).skillId!!
                    skillRepository.getSkill(skillId)
                } else {
                    skillRepository.getSkill(2)
                }
                when (skill) {
                    is AttackSkill -> "攻撃"
                    is HealSkill -> "回復"
                }
            }

            ActionType.None -> throw RuntimeException(
                "ここには来ないはず"
            )
        }
    }

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is AttackPhaseCommand
    }

    override fun goNextImpl() {
        CoroutineScope(Dispatchers.IO).launch {
            if (attackingPlayerId.value < playerNum) {
                playerAction()
            } else {
                enemyAction()
            }

            delay(100)

            if (commandStateRepository.nowCommandType != FinishCommand) {
                changeToNextCharacter()
            }
        }
    }

    private suspend fun playerAction() {
        when (actionRepository.getAction(attackingPlayerId.value).thisTurnAction) {
            ActionType.Normal -> {
                //　攻撃
                attackFromPlayerUseCase(
                    target = actionRepository.getAction(attackingPlayerId.value).target,
                    damage = 10,
                )
            }

            ActionType.Skill -> {
                skillAction(
                    id = attackingPlayerId.value,
                    actionData = actionRepository.getAction(attackingPlayerId.value),
                    statusList = battleMonsterRepository.getMonsters(),
                    attackUseCase = attackFromPlayerUseCase,
                    updateParameter = updatePlayerParameter,
                )
            }

            ActionType.None -> Unit
        }

        // 敵を倒していたらバトル終了
        if (isAllMonsterNotActiveUseCase()) {
            commandStateRepository.push(
                FinishCommand
            )
            return
        }
    }

    private suspend fun enemyAction() {

        skillAction(
            id = attackingPlayerId.value - playerNum,
            statusList = playerRepository.getPlayers(),
            actionData = ActionData(
                skillId = ATTACK_NORMAL,
                target = 0,
            ),
            attackUseCase = attackFromEnemyUseCase,
            updateParameter = updateEnemyParameter,
        )
    }

    private suspend fun skillAction(
        id: Int,
        actionData: ActionData,
        statusList: List<Status>,
        attackUseCase: AttackUseCase,
        updateParameter: UpdateStatusService<*>,
    ) {
        val skill = skillRepository.getSkill(
            id = actionData.skillId
                ?: throw RuntimeException("スキルを選んでいるのでスキルが入っているはず")
        )

        // MP減らす
        updateParameter.decMP(
            id = id,
            amount = skill.needMP,
        )

        when (skill) {
            is AttackSkill -> {
                val targetList = findActiveTargetUseCase.invoke(
                    statusList = statusList,
                    target = actionData.target,
                    targetNum = skill.targetNum
                )

                //　複数の対象攻撃
                targetList.forEach {
                    attackUseCase.invoke(
                        target = it,
                        damage = skill.damageAmount,
                    )
                }
            }

            is HealSkill -> {
                val target = actionData.ally
                updateParameter.incHP(
                    id = target,
                    amount = skill.healAmount,
                )
            }
        }
    }


    private fun changeToNextCharacter() {
        val totalNum = playerNum + battleMonsterRepository.getMonsters().size
        while (true) {
            //　次のキャラに移動
            mutableAttackingPlayerId.value++

            // 全員行動が終わっていたら初期状態へ
            if (mutableAttackingPlayerId.value
                >= totalNum
            ) {
                mutableAttackingPlayerId.value = 0
                commandStateRepository.init()
                break
            }

            if (mutableAttackingPlayerId.value >= playerNum) {
                //　monsterを確認
                if (battleMonsterRepository.getStatus(
                        mutableAttackingPlayerId.value - playerNum
                    ).isActive
                ) {
                    break
                }
            } else {
                // 行動可能なら行動する
                if (playerRepository.getStatus(attackingPlayerId.value).isActive &&
                    actionRepository.getAction(attackingPlayerId.value).thisTurnAction != ActionType.None
                ) {
                    break
                }
            }

            //行動可能ではないので次のキャラへ移動
        }
    }
}
