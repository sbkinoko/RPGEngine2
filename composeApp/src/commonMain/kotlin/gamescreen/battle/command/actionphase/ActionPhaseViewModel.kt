package gamescreen.battle.command.actionphase

import core.domain.item.skill.AttackSkill
import core.domain.item.skill.HealSkill
import core.domain.item.tool.HealTool
import core.domain.status.Status
import core.repository.battlemonster.BattleMonsterRepository
import core.repository.player.PlayerStatusRepository
import core.usecase.item.usetool.UseToolUseCase
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import core.usecase.updateparameter.UpdateStatusUseCase
import data.item.skill.ATTACK_NORMAL
import data.item.skill.SkillRepository
import data.item.tool.ToolRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.QualifierAttackFromEnemy
import gamescreen.battle.QualifierAttackFromPlayer
import gamescreen.battle.domain.ActionData
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.AttackPhaseCommand
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.FinishCommand
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.service.isannihilation.IsAnnihilationService
import gamescreen.battle.usecase.attack.AttackUseCase
import gamescreen.battle.usecase.decideactionbyspeed.DecideActionOrderUseCase
import gamescreen.battle.usecase.findactivetarget.FindActiveTargetUseCase
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import values.Constants.Companion.playerNum

class ActionPhaseViewModel(
    private val decideActionOrderUseCase: DecideActionOrderUseCase,
) : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()
    private val playerStatusRepository: PlayerStatusRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val toolRepository: ToolRepository by inject()

    private val updatePlayerParameter: UpdatePlayerStatusUseCase by inject()
    private val updateEnemyParameter: UpdateMonsterStatusUseCase by inject()

    private val attackFromPlayerUseCase: AttackUseCase by inject(
        qualifier = named(QualifierAttackFromPlayer)
    )
    private val attackFromEnemyUseCase: AttackUseCase by inject(
        qualifier = named(QualifierAttackFromEnemy)
    )
    private val findActiveTargetUseCase: FindActiveTargetUseCase by inject()

    private val isAnnihilationService: IsAnnihilationService by inject()

    private val isMonsterAnnihilated: Boolean
        get() = isAnnihilationService(
            battleMonsterRepository.getMonsters()
        )

    private val isPlayerAnnihilated: Boolean
        get() = isAnnihilationService(
            playerStatusRepository.getPlayers()
        )

    private val useToolUseCase: UseToolUseCase by inject()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            this@ActionPhaseViewModel.commandRepository.commandStateFlow.collect {
                if (it is AttackPhaseCommand) {
                    resetAttackingPlayer()
                }
            }
        }
    }

    private var attackingNumber = NONE_PLAYER

    private val mutableAttackingStatusId: MutableStateFlow<Int> =
        MutableStateFlow(NONE_PLAYER)
    val attackingStatusId = mutableAttackingStatusId.asStateFlow()

    private val monsterNum: Int
        get() = battleMonsterRepository.getMonsters().size

    // 使わないので適当
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    private lateinit var speedList: List<Int>

    private val statusId: Int
        get() = speedList[attackingNumber]

    fun init() {
        speedList = decideActionOrderUseCase.invoke()
        changeToNextCharacter()
    }

    fun getActionText(playerId: Int): String {
        if (playerId < 0) {
            return ""
        }

        val actionStatusName = getActionStatusName(playerId)
        val targetStatusName = getTargetStatusName(playerId)
        val actionStatusActionName = getActionName(playerId)

        return actionStatusName + "の" +
                targetStatusName + "への" +
                actionStatusActionName
    }

    private fun getActionStatusName(id: Int): String {
        return if (isPlayer(id = id)) {
            playerStatusRepository.getStatus(id).name
        } else {
            battleMonsterRepository.getStatus(id.toMonster()).name
        }
    }

    private enum class Type {
        ATTACK,
        HEAL,
    }

    private fun getTargetStatusName(id: Int): String {
        return if (isPlayer(id = id)) {
            getPlayerActionTargetName(id = id)
        } else {
            getMonsterTargetName()
        }
    }

    private fun getPlayerActionTargetName(id: Int): String {
        val action = actionRepository.getAction(id)

        val type = when (action.thisTurnAction) {
            ActionType.Normal -> Type.ATTACK
            ActionType.Skill -> when (skillRepository.getItem(action.skillId)) {
                is AttackSkill -> Type.ATTACK
                is HealSkill -> Type.HEAL
            }

            ActionType.TOOL -> when (toolRepository.getItem(action.toolId)) {
                is HealTool -> Type.HEAL
            }

            ActionType.None ->
                throw RuntimeException("ここには来ない")
        }

        return when (type) {
            Type.ATTACK -> {
                val targetId = action.target
                battleMonsterRepository.getStatus(targetId).name
            }

            Type.HEAL -> {
                val targetId = action.ally
                playerStatusRepository.getStatus(targetId).name
            }
        }
    }

    private fun getMonsterTargetName(): String {
        //　fixme 敵の攻撃対象を保存するようにしたら修正
        return "仲間"
    }

    private fun getActionType(id: Int): ActionData {
        return if (isPlayer(id = id)) {
            actionRepository.getAction(id)
        } else {
            ActionData(
                thisTurnAction = ActionType.Skill,
                skillId = ATTACK_NORMAL,
                target = 0,
                ally = 0,
                toolId = 0,
                toolIndex = 0,
            )
        }
    }

    private fun getActionName(
        id: Int,
    ): String {
        val action = getActionType(id = id).thisTurnAction

        return when (action) {
            ActionType.Normal -> "攻撃"
            ActionType.Skill -> {
                val skill = if (isPlayer(id = id)) {
                    val skillId = actionRepository.getAction(id).skillId
                    skillRepository.getItem(skillId)
                } else {
                    skillRepository.getItem(ATTACK_NORMAL)
                }
                when (skill) {
                    is AttackSkill -> "攻撃"
                    is HealSkill -> "回復"
                }
            }

            ActionType.TOOL -> {
                val itemId = actionRepository.getAction(id).toolId
                val item = toolRepository.getItem(itemId)
                when (item) {
                    is HealTool -> "回復"
                }
            }

            ActionType.None -> throw RuntimeException(
                "ここには来ないはず"
            )
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is AttackPhaseCommand
    }

    override fun goNextImpl() {
        CoroutineScope(Dispatchers.IO).launch {
            val id = statusId
            if (isPlayer(id = id)) {
                playerAction()
            } else {
                enemyAction()
            }

            delay(100)

            // アクションをしてもまだ戦闘中なら次のキャラへ
            if (this@ActionPhaseViewModel.commandRepository.nowBattleCommandType !is FinishCommand) {
                changeToNextCharacter()
            }
        }
    }

    private suspend fun playerAction() {
        val id = statusId
        val actionType = getActionType(id).thisTurnAction
        when (actionType) {
            ActionType.Normal -> {
                //　攻撃
                attackFromPlayerUseCase(
                    target = actionRepository.getAction(id).target,
                    damage = 10,
                )
            }

            ActionType.Skill -> {
                skillAction(
                    id = id,
                    actionData = actionRepository.getAction(id),
                    statusList = battleMonsterRepository.getMonsters(),
                    attackUseCase = attackFromPlayerUseCase,
                    updateParameter = updatePlayerParameter,
                )
            }

            ActionType.TOOL -> {
                toolAction(
                    userId = id,
                    actionData = actionRepository.getAction(
                        id,
                    ),
                )
            }

            ActionType.None -> Unit
        }

        // 敵を倒していたらバトル終了
        if (isMonsterAnnihilated) {
            this.commandRepository.push(
                FinishCommand(
                    isWin = true
                )
            )
            return
        }
    }

    private suspend fun enemyAction() {
        val id = statusId
        skillAction(
            id = id.toMonster(),
            statusList = playerStatusRepository.getPlayers(),
            actionData = ActionData(
                skillId = ATTACK_NORMAL,
                target = 0,
                ally = 0,
                toolId = 0,
                toolIndex = 0,
            ),
            attackUseCase = attackFromEnemyUseCase,
            updateParameter = updateEnemyParameter,
        )

        if (isPlayerAnnihilated) {
            this.commandRepository.push(
                FinishCommand(
                    isWin = false
                )
            )
            return
        }
    }

    private suspend fun skillAction(
        id: Int,
        actionData: ActionData,
        statusList: List<Status>,
        attackUseCase: AttackUseCase,
        updateParameter: UpdateStatusUseCase<*>,
    ) {
        val skill = skillRepository.getItem(
            id = actionData.skillId
        )

        // MP減らす
        updateParameter.decMP(
            id = id,
            amount = skill.needMP,
        )

        // todo 敵のスキルをつくってからマップとの共通化を考える
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

    private fun toolAction(
        userId: Int,
        actionData: ActionData,
    ) {
        useToolUseCase.invoke(
            userId = userId,
            index = actionData.toolIndex,
            targetId = actionData.target,
        )
    }

    private val totalNum: Int
        get() = playerNum + monsterNum

    private fun changeToNextCharacter() {
        while (true) {
            //　次のキャラに移動
            attackingNumber++

            // 全キャラ終わっているので別の処理へ
            if (totalNum <= attackingNumber) {
                initAction()
                break
            }

            val id = statusId

            if (isPlayer(id = id)) {
                //　playerを確認

                val player = playerStatusRepository.getStatus(id = id)
                if (player.isActive.not()) {
                    continue
                }

                val action = actionRepository.getAction(playerId = id)
                val actionType = action.thisTurnAction
                if (actionType == ActionType.None) {
                    continue
                }
            } else {
                val monsterId = id.toMonster()
                val monster = battleMonsterRepository
                    .getStatus(id = monsterId)

                //　monsterを確認
                if (monster.isActive.not()) {
                    continue
                }
            }

            // 行動可能なのでデータ更新
            mutableAttackingStatusId.value = id
            break
        }
    }

    private fun isPlayer(id: Int): Boolean {
        return id < playerNum
    }

    private fun Int.toMonster(): Int = this - playerNum


    private fun initAction() {
        resetAttackingPlayer()
        commandRepository.init()
    }

    private fun resetAttackingPlayer() {
        mutableAttackingStatusId.value = NONE_PLAYER
        attackingNumber = NONE_PLAYER
    }

    override fun pressB() {
        pressA()
    }

    companion object {
        private const val NONE_PLAYER = -1
    }
}
