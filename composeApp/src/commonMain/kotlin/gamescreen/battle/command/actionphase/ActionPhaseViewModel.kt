package gamescreen.battle.command.actionphase

import androidx.compose.runtime.mutableStateOf
import core.domain.item.AttackItem
import core.domain.item.ConditionItem
import core.domain.item.CostType
import core.domain.item.DamageType
import core.domain.item.HealItem
import core.domain.item.Item
import core.domain.item.TypeKind
import core.domain.item.skill.AttackSkill
import core.domain.item.skill.ConditionSkill
import core.domain.item.skill.HealSkill
import core.domain.status.Character
import core.domain.status.StatusData
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
import gamescreen.battle.domain.StatusType
import gamescreen.battle.domain.StatusWrapper
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.service.isannihilation.IsAnnihilationService
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


    private var statusWrapperList: List<StatusWrapper> = emptyList()


    fun init() {
        val list = mutableListOf<StatusWrapper>()
        playerStatusRepository.getPlayers()
            .mapIndexed { id, status ->
                list += StatusWrapper(
                    status = status,
                    id = id,
                    actionData = actionRepository.getAction(playerId = id),
                    statusType = StatusType.Player,
                    newId = id,
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
                    statusType = StatusType.Enemy,
                    newId = index,
                )
            }
        statusWrapperList = list

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

        val actionStatusName = getStatus(playerId)
            .name

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

            // fixme 共通化したい　〇〇が治った
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


    private fun getActionData(id: Int): ActionData {
        return statusWrapperList[id].actionData
    }

    private fun getStatus(characterId: Int): StatusData {
        val repo = when (statusWrapperList[characterId].statusType) {
            StatusType.Player -> playerStatusRepository
            StatusType.Enemy -> battleInfoRepository
        }

        return repo
            .getStatus(statusWrapperList[characterId].newId)
            .statusData
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
            is AttackItem,
                -> {
                var targetId = action.target
                if (battleInfoRepository.getStatus(targetId)
                        .statusData.isActive.not()
                ) {
                    targetId = findActiveTargetUseCase.invoke(
                        statusList = battleInfoRepository.getMonsters(),
                        target = targetId,
                        targetNum = item.targetNum,
                    ).first()
                }
                battleInfoRepository.getStatus(targetId).statusData.name
            }

            is HealItem -> {
                val targetId = action.ally
                playerStatusRepository.getStatus(targetId).statusData.name
            }
        }
    }

    private fun getMonsterTargetName(id: Int): String {
        val action = statusWrapperList[id].actionData
        val item = getActionItem(id)

        return when (item as TypeKind) {
            is ConditionItem,
            is AttackItem,
                -> {
                var targetId = action.target
                if (playerStatusRepository.getStatus(targetId).statusData.isActive.not()) {
                    targetId = findActiveTargetUseCase.invoke(
                        statusList = playerStatusRepository.getPlayers(),
                        target = targetId,
                        targetNum = item.targetNum,
                    ).first()
                }
                playerStatusRepository.getStatus(targetId).statusData.name
            }

            is HealItem -> {
                val targetId = action.ally
                battleInfoRepository.getStatus(targetId).statusData.name
            }
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is AttackPhaseCommand
    }

    override fun goNextImpl() {
        CoroutineScope(Dispatchers.IO).launch {
            when (actionState.value) {
                ActionState.Start,
                ActionState.Next,
                    -> Unit

                ActionState.Paralyze -> {
                    // fixme changeでいい感じにできるようにしたい
                    // アクションをスキップしたい
                    actionState.value = ActionState.Action
                    changeActionPhase()
                }

                ActionState.Action,
                is ActionState.Poison,
                is ActionState.CurePoison,
                is ActionState.CureParalyze,
                    -> {
                    delay(100)
                    checkBattleFinish()
                    changeActionPhase()
                }
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
                    attacker = statusWrapperList[id].status.statusData,
                    damageType = DamageType.AtkMultiple(1),
                )
            }

            ActionType.Skill -> {
                skillAction(
                    id = id,
                    statusData = statusWrapperList[id].status.statusData,
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
    }

    private suspend fun enemyAction() {
        val id = statusId
        skillAction(
            id = id.toMonster(),
            statusData = statusWrapperList[id].status.statusData,
            statusList = playerStatusRepository.getPlayers(),
            actionData = statusWrapperList[id].actionData,
            attackUseCase = attackFromEnemyUseCase,
            conditionUseCase = conditionFromEnemyUseCase,
            updateParameter = updateEnemyParameter,
        )
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
        statusData: StatusData,
        actionData: ActionData,
        statusList: List<Character>,
        attackUseCase: AttackUseCase,
        conditionUseCase: ConditionUseCase,
        updateParameter: UpdateStatusUseCase<*>,
    ) {
        val skill = skillRepository.getItem(
            id = actionData.skillId
        )

        //コスト処理
        when (val costType = skill.costType) {
            is CostType.MP -> {
                updateParameter.decMP(
                    id = id,
                    amount = costType.needMP,
                )
            }

            // 道具なので特に処理なし
            CostType.Consume,
            CostType.NotConsume,
                -> Unit
        }

        // todo 複数回攻撃する技を作ったら表示方法を考える
        // todo 味方を選択するスキルで対象が対象外になっている場合の挙動を作成する
        // todo 敵のスキルをつくってからマップとの共通化を考える
        when (skill) {
            is AttackSkill -> {
                val targetList = findActiveTargetUseCase.invoke(
                    statusList = statusList,
                    target = actionData.target,
                    targetNum = skill.targetNum,
                )

                //　複数の対象攻撃
                targetList.forEach {
                    attackUseCase.invoke(
                        target = it,
                        attacker = statusData,
                        damageType = skill.damageType,
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
                if (player.statusData.isActive.not()) {
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
                if (monster.statusData.isActive.not()) {
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

        // fixme getNextStateの引数にstatusを追加して、その中で処理したい
        // ステータスに関連する処理も内部で行うようにする
        if (statusWrapperList[statusId].status.statusData.isActive.not()) {
            // 倒れていたらnextに変更
            actionState.value = ActionState.Next
            return
        }

        // 状態の切り替え
        val nextState = actionState.value.getNextState(
            conditionList = statusWrapperList[statusId]
                .status
                .statusData
                .conditionList
        )
        actionState.value = nextState

        // 切り替え後の状態で処理を実行
        CoroutineScope(Dispatchers.Default).launch {
            when (nextState) {
                ActionState.Action -> {
                    // fixme delayをなくせるようにする
                    // delayを消すと何も表示されないことがある
                    delay(100)
                    if (isPlayer(id = statusId)) {
                        playerAction()
                    } else {
                        enemyAction()
                    }
                }

                is ActionState.CureParalyze,
                is ActionState.CurePoison,
                    -> {
                    val cured = (nextState as ActionState.Cure).list
                    if (isPlayer(statusId)) {
                        updatePlayerParameter.updateConditionList(
                            id = statusId,
                            conditionList = cured,
                        )
                    } else {
                        updateEnemyParameter.updateConditionList(
                            id = statusId.toMonster(),
                            conditionList = cured
                        )
                    }
                }

                ActionState.Next -> {
                    // アクションをしてもまだ戦闘中なら次のキャラへ
                    if (this@ActionPhaseViewModel.commandRepository.nowBattleCommandType !is FinishCommand) {
                        changeToNextCharacter()
                    }
                }

                ActionState.Paralyze -> Unit
                is ActionState.Poison -> {
                    if (isPlayer(statusId)) {
                        updatePlayerParameter.decHP(
                            id = statusId,
                            amount = nextState.damage,
                        )
                    } else {
                        updateEnemyParameter.decHP(
                            id = statusId.toMonster(),
                            amount = nextState.damage,
                        )
                    }
                }

                ActionState.Start -> Unit
            }
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
