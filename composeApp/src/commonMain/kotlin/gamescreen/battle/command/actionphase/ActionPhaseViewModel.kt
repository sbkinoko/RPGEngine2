package gamescreen.battle.command.actionphase

import androidx.compose.runtime.mutableStateOf
import core.domain.item.AttackItem
import core.domain.item.ConditionItem
import core.domain.item.HealItem
import core.domain.item.Item
import core.domain.item.TypeKind
import core.domain.item.skill.AttackSkill
import core.domain.item.skill.ConditionSkill
import core.domain.item.skill.HealSkill
import core.domain.status.ConditionType
import core.domain.status.Status
import core.domain.status.tryCalcPoisonDamage
import core.domain.status.tryCure
import core.repository.battlemonster.BattleInfoRepository
import core.repository.player.PlayerStatusRepository
import core.usecase.item.usetool.UseToolUseCase
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import core.usecase.updateparameter.UpdateStatusUseCase
import data.item.skill.SkillId
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
import gamescreen.battle.domain.StatusWrapper
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.service.isannihilation.IsAnnihilationService
import gamescreen.battle.service.judgemovebyparalyze.canMove
import gamescreen.battle.service.monster.DecideMonsterActionService
import gamescreen.battle.usecase.attack.AttackUseCase
import gamescreen.battle.usecase.condition.ConditionUseCase
import gamescreen.battle.usecase.decideactionorder.DecideActionOrderUseCase
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
    private val battleInfoRepository: BattleInfoRepository by inject()
    private val playerStatusRepository: PlayerStatusRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val toolRepository: ToolRepository by inject()

    private val updatePlayerParameter: UpdatePlayerStatusUseCase by inject()
    private val updateEnemyParameter: UpdateMonsterStatusUseCase by inject()

    private val attackFromPlayerUseCase: AttackUseCase by inject(
        qualifier = named(QualifierAttackFromPlayer)
    )
    private val conditionFromPlayerUseCase: ConditionUseCase by inject(
        qualifier = named(QualifierAttackFromPlayer)
    )

    private val attackFromEnemyUseCase: AttackUseCase by inject(
        qualifier = named(QualifierAttackFromEnemy)
    )
    private val conditionFromEnemyUseCase: ConditionUseCase by inject(
        qualifier = named(QualifierAttackFromEnemy)
    )

    private val findActiveTargetUseCase: FindActiveTargetUseCase by inject()

    private val isAnnihilationService: IsAnnihilationService by inject()

    private val decideMonsterActionService = DecideMonsterActionService()

    private val isMonsterAnnihilated: Boolean
        get() = isAnnihilationService(
            battleInfoRepository.getMonsters()
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
        get() = battleInfoRepository.getMonsters().size

    // 使わないので適当
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    private lateinit var speedList: List<Int>

    private val statusId: Int
        get() = speedList[attackingNumber]

    val actionState =
        mutableStateOf<ActionState>(ActionState.Start)

    private val statusWrapperList: List<StatusWrapper>
        get() {
            val list = mutableListOf<StatusWrapper>()
            playerStatusRepository.getPlayers()
                .mapIndexed { id, status ->
                    list += StatusWrapper(
                        status = status,
                        id = id,
                        actionData = actionRepository.getAction(playerId = id),
                    )
                }

            battleInfoRepository.getMonsters()
                .mapIndexed { index, status ->
                    val action = decideMonsterActionService.getAction(
                        status,
                        playerStatusRepository.getPlayers(),
                    )
                    list += StatusWrapper(
                        status = status,
                        id = index + playerNum,
                        actionData = action,
                    )
                }
            return list
        }

    fun init() {
        speedList = decideActionOrderUseCase.invoke(
            statusList = statusWrapperList,
        )
        changeToNextCharacter()
    }


    fun getActionText(
        playerId: Int,
        actionState: ActionState,
    ): String {
        if (playerId < 0) {
            return ""
        }

        val actionStatusName = getActionStatusName(playerId)
        val targetStatusName = getTargetStatusName(playerId)
        val actionName = getActionItem(playerId).name

        return when (actionState) {
            ActionState.Paralyze -> {
                actionStatusName + "はしびれて動けない"
            }

            ActionState.Action -> {
                return actionStatusName + "の" +
                        targetStatusName + "への" +
                        actionName
            }

            is ActionState.Poison -> {
                actionStatusName + "は毒のダメージを受けた"
            }

            is ActionState.CurePoison -> {
                actionStatusName + "の毒が治った"
            }

            is ActionState.CureParalyze -> {
                actionStatusName + "の麻痺が治った"
            }

            // 表示するものはない
            ActionState.Start -> ""
            ActionState.Next -> ""
        }
    }

    private fun getActionStatusName(id: Int): String {
        return statusWrapperList[id].status.name
    }

    private fun getActionData(id: Int): ActionData {
        return statusWrapperList[id].actionData
    }

    private fun getActionItem(id: Int): Item {
        val action = getActionData(id = id)
        return when (action.thisTurnAction) {
            ActionType.Normal -> skillRepository.getItem(SkillId.Normal1)
            ActionType.Skill -> skillRepository.getItem(action.skillId)
            ActionType.TOOL -> toolRepository.getItem(action.toolId)

            ActionType.None ->
                throw RuntimeException("ここには来ない")
        }
    }

    private fun getTargetStatusName(id: Int): String {
        return if (isPlayer(id = id)) {
            getPlayerActionTargetName(id = id)
        } else {
            getMonsterTargetName(id = id)
        }
    }

    private fun getPlayerActionTargetName(id: Int): String {
        val action = statusWrapperList[id].actionData
        val item = getActionItem(id = id)

        return when (item as TypeKind) {
            is ConditionItem,
            is AttackItem -> {
                var targetId = action.target
                if (battleInfoRepository.getStatus(targetId).isActive.not()) {
                    targetId = findActiveTargetUseCase.invoke(
                        statusList = battleInfoRepository.getMonsters(),
                        target = targetId,
                        targetNum = item.targetNum,
                    ).first()
                }
                battleInfoRepository.getStatus(targetId).name
            }

            is HealItem -> {
                val targetId = action.ally
                playerStatusRepository.getStatus(targetId).name
            }
        }
    }

    private fun getMonsterTargetName(id: Int): String {
        val action = statusWrapperList[id].actionData
        val item = getActionItem(id)

        return when (item as TypeKind) {
            is ConditionItem,
            is AttackItem -> {
                var targetId = action.target
                if (playerStatusRepository.getStatus(targetId).isActive.not()) {
                    targetId = findActiveTargetUseCase.invoke(
                        statusList = playerStatusRepository.getPlayers(),
                        target = targetId,
                        targetNum = item.targetNum,
                    ).first()
                }
                playerStatusRepository.getStatus(targetId).name
            }

            is HealItem -> {
                val targetId = action.ally
                battleInfoRepository.getStatus(targetId).name
            }
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is AttackPhaseCommand
    }

    override fun goNextImpl() {
        CoroutineScope(Dispatchers.IO).launch {
            val id = statusId

            when (actionState.value) {
                ActionState.Start -> Unit

                ActionState.Paralyze -> {
                    // アクションをスキップしたい
                    actionState.value = ActionState.Action
                    changeActionPhase()
                }

                ActionState.Action -> {
                    if (isPlayer(id = id)) {
                        playerAction()
                    } else {
                        enemyAction()
                    }
                    delay(100)
                    // アクションの次の状態に遷移する
                    changeActionPhase()
                }

                is ActionState.Poison -> {
                    checkBattleFinish()
                    delay(100)
                    changeActionPhase()
                }

                is ActionState.CurePoison,
                is ActionState.CureParalyze -> {
                    changeActionPhase()
                }

                // 最後の処理なので別途用意
                ActionState.Next -> Unit
            }

            if (actionState.value == ActionState.Next) {
                // アクションをしてもまだ戦闘中なら次のキャラへ
                if (this@ActionPhaseViewModel.commandRepository.nowBattleCommandType !is FinishCommand) {
                    changeToNextCharacter()
                }
                return@launch
            }
        }
    }

    private suspend fun playerAction() {
        val id = statusId
        val actionType = statusWrapperList[id].actionData.thisTurnAction

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
                    statusList = battleInfoRepository.getMonsters(),
                    attackUseCase = attackFromPlayerUseCase,
                    conditionUseCase = conditionFromPlayerUseCase,
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
            actionData = statusWrapperList[id].actionData,
            attackUseCase = attackFromEnemyUseCase,
            conditionUseCase = conditionFromEnemyUseCase,
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

    private fun checkBattleFinish() {
        // 同時に全滅したら負け

        // プレイヤーが全滅していたらバトル終了
        if (isPlayerAnnihilated) {
            this.commandRepository.push(
                FinishCommand(
                    isWin = false
                )
            )
            return
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

    private suspend fun skillAction(
        id: Int,
        actionData: ActionData,
        statusList: List<Status>,
        attackUseCase: AttackUseCase,
        conditionUseCase: ConditionUseCase,
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

        // todo 複数回攻撃する技を作ったら表示方法を考える
        // todo 味方を選択するスキルで対象が対象外になっている場合の挙動を作成する
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

            is ConditionSkill -> {
                val targetList = findActiveTargetUseCase.invoke(
                    statusList = statusList,
                    target = actionData.target,
                    targetNum = skill.targetNum
                )
                //　複数の対象攻撃
                targetList.forEach {
                    conditionUseCase.invoke(
                        target = it,
                        conditionType = skill.conditionType,
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
                val monster = battleInfoRepository
                    .getStatus(id = monsterId)

                //　monsterを確認
                if (monster.isActive.not()) {
                    continue
                }
            }

            // 行動可能なのでデータ更新
            mutableAttackingStatusId.value = id
            actionState.value = ActionState.Start
            changeActionPhase()
            break
        }
    }

    private fun changeActionPhase() {
        if (statusWrapperList[statusId].status.isActive.not()) {
            // 倒れていたらnextに変更
            actionState.value = ActionState.Next
            return
        }

        while (true) {

            actionState.value = actionState.value.next

            when (actionState.value) {
                // 初期状態で固定されることはない
                ActionState.Start -> continue

                ActionState.Paralyze -> {
                    val paralyzeList = statusWrapperList[statusId]
                        .status
                        .conditionList
                        .filterIsInstance<ConditionType.Paralysis>()

                    if (paralyzeList.isEmpty()) {
                        // 麻痺に関する処理はないので処理を続ける
                        continue
                    }

                    val canMove = paralyzeList.canMove()

                    if (!canMove) {
                        // 動けないので状態が確定
                        return
                    }

                    //　動けるので次のstateで処理を続ける
                    continue
                }

                ActionState.Action -> {
                    // actionで状態を固定
                    return
                }

                is ActionState.Poison -> {
                    val list = statusWrapperList[statusId]
                        .status
                        .conditionList

                    val damage = list.tryCalcPoisonDamage()

                    if (damage == 0) {
                        continue
                    }

                    CoroutineScope(Dispatchers.Default).launch {
                        if (isPlayer(statusId)) {
                            updatePlayerParameter.decHP(
                                id = statusId,
                                amount = damage,
                            )
                        } else {
                            updateEnemyParameter.decHP(
                                id = statusId.toMonster(),
                                amount = damage,
                            )
                        }
                    }

                    // 毒でダメージを受けたので状態を固定
                    return
                }

                is ActionState.CurePoison -> {
                    if (tryCure<ConditionType.Poison>()) {
                        // 毒が治った状態で固定
                        return
                    }
                    // 毒を直す処理はないので次を探す
                    continue
                }

                is ActionState.CureParalyze -> {
                    if (tryCure<ConditionType.Paralysis>()) {
                        // 麻痺が治った状態で固定
                        return
                    }

                    //麻痺を直す処理はないので次を探す
                    continue
                }

                ActionState.Next -> {
                    // nextで状態を固定
                    return
                }
            }
        }
    }

    private inline fun <reified T : ConditionType.CureProb> tryCure(): Boolean {
        val beforeList = statusWrapperList[statusId]
            .status
            .conditionList
        val after = beforeList
            .tryCure<T>()

        if (beforeList.size == after.size) {
            return false
        }

        CoroutineScope(Dispatchers.Default).launch {
            if (isPlayer(statusId)) {
                updatePlayerParameter.updateConditionList(
                    id = statusId,
                    conditionList = after,
                )
            } else {
                updateEnemyParameter.updateConditionList(
                    id = statusId.toMonster(),
                    conditionList = after
                )
            }
        }
        return true
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
