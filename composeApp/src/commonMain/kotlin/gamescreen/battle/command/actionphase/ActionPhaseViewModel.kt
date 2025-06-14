package gamescreen.battle.command.actionphase

import androidx.compose.runtime.mutableStateOf
import core.UpdatePlayer
import core.domain.item.AttackEffect
import core.domain.item.BufEffect
import core.domain.item.ConditionEffect
import core.domain.item.CostType
import core.domain.item.DamageType
import core.domain.item.EffectKind
import core.domain.item.HealEffect
import core.domain.item.Item
import core.domain.item.TargetType
import core.domain.status.Character
import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusType
import core.domain.status.param.EXP
import core.repository.battlemonster.BattleInfoRepository
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import core.usecase.item.usetool.UseToolUseCase
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
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

    private val statusDataRepository: StatusDataRepository<StatusType.Player>,
) : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val battleInfoRepository: BattleInfoRepository by inject()
    private val playerStatusRepository: PlayerStatusRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val toolRepository: ToolRepository by inject()

    private val updatePlayerParameter: UpdateStatusUseCase<StatusType.Player> by inject(
        qualifier = named(UpdatePlayer)
    )
    private val updateEnemyParameter: UpdateMonsterStatusUseCase by inject()

    private val attackFromPlayerUseCase: AttackUseCase<StatusType.Player> by inject(
        qualifier = named(
            QualifierAttackFromPlayer
        )
    )

    private val conditionFromPlayerUseCase: ConditionUseCase by inject(
        qualifier = named(QualifierAttackFromPlayer)
    )

    private val attackFromEnemyUseCase: AttackUseCase<StatusType.Enemy> by inject(
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
            battleInfoRepository.getStatusList()
        )

    private val isPlayerAnnihilated: Boolean
        get() = isAnnihilationService(
            playerStatusRepository.getStatusList()
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

    private val mutableAttackingStatusId: MutableStateFlow<StatusWrapper> =
        MutableStateFlow(dummyStatus)
    val attackingStatusId = mutableAttackingStatusId.asStateFlow()

    private val monsterNum: Int
        get() = battleInfoRepository.getStatusList().size

    // 使わないので適当
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    private lateinit var speedList: List<StatusWrapper>

    private val actionStatusWrapper: StatusWrapper
        get() = speedList[attackingNumber]

    val actionState =
        mutableStateOf<ActionState>(ActionState.Start)

    private var statusWrapperList: List<StatusWrapper> = emptyList()

    fun init() {
        val list = mutableListOf<StatusWrapper>()
        playerStatusRepository.getStatusList()
            .mapIndexed { id, status ->
                list += StatusWrapper(
                    status = status,
                    actionData = actionRepository.getAction(playerId = id),
                    statusType = StatusType.Player,
                    newId = id,
                )
            }

        battleInfoRepository.getStatusList()
            .mapIndexed { index, status ->
                val action = decideMonsterActionService.getAction(
                    status,
                    playerStatusRepository.getStatusList(),
                )
                list += StatusWrapper(
                    status = status,
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
        statusWrapper: StatusWrapper,
        actionState: ActionState,
    ): String {
        if (statusWrapper.statusType == StatusType.None) {
            return ""
        }

        val actionStatusName = getStatus(statusWrapper)
            .name

        val targetStatusName = getTargetStatusName(statusWrapper)
        val actionName = getActionItem(statusWrapper).name

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

    /**
     * statusWrapperを入れて対応するステータスを取得
     */
    private fun getStatus(statusWrapper: StatusWrapper): StatusData<*> {
        return when (statusWrapper.statusType) {
            StatusType.Player -> statusDataRepository.getStatusData(
                statusWrapper.newId
            )

            StatusType.Enemy -> battleInfoRepository.getStatus(statusWrapper.newId).statusData
            StatusType.None -> throw RuntimeException("Noneで処理はない")
        }
    }

    private fun getActionItem(statusWrapper: StatusWrapper): Item {
        val action = statusWrapper.actionData
        return when (action.thisTurnAction) {
            ActionType.Normal -> skillRepository.getItem(SkillId.Normal1)
            ActionType.Skill -> skillRepository.getItem(action.skillId)
            ActionType.TOOL -> toolRepository.getItem(action.toolId)

            ActionType.None ->
                throw RuntimeException("ここには来ない")
        }
    }

    private fun getTargetStatusName(statusWrapper: StatusWrapper): String {
        return when (statusWrapper.statusType) {
            StatusType.Player -> getPlayerActionTargetName(statusWrapper = statusWrapper)
            StatusType.Enemy -> getMonsterTargetName(statusWrapper = statusWrapper)
            StatusType.None -> throw RuntimeException("Noneで処理はない")
        }
    }

    private fun getPlayerActionTargetName(statusWrapper: StatusWrapper): String {
        val action = statusWrapper.actionData
        val item = getActionItem(statusWrapper = statusWrapper)

        val attackerRepository = statusDataRepository

        return when (item.targetType) {
            TargetType.Enemy,
                -> {
                var targetId = action.target
                if (battleInfoRepository.getStatus(targetId)
                        .statusData.isActive.not()
                ) {
                    targetId = findActiveTargetUseCase.invoke(
                        statusList = battleInfoRepository.getStatusList().map { it.statusData },
                        target = targetId,
                        targetNum = item.targetNum,
                    ).first()
                }
                battleInfoRepository.getStatus(targetId).statusData.name
            }

            TargetType.Ally -> {
                val targetId = action.ally
                attackerRepository.getStatusData(targetId).name
            }
        }
    }

    private fun getMonsterTargetName(statusWrapper: StatusWrapper): String {
        val action = statusWrapper.actionData
        val item = getActionItem(statusWrapper = statusWrapper)

        return when (item.targetType) {
            TargetType.Enemy -> {
                var targetId = action.target
                if (statusDataRepository.getStatusData(targetId).isActive.not()) {
                    targetId = findActiveTargetUseCase.invoke(
                        statusList = statusDataRepository.getStatusList(),
                        target = targetId,
                        targetNum = item.targetNum,
                    ).first()
                }
                statusDataRepository.getStatusData(targetId).name
            }

            TargetType.Ally -> {
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
        val actionType = actionStatusWrapper.actionData.thisTurnAction

        when (actionType) {
            ActionType.Normal -> {
                //　攻撃
                attackFromPlayerUseCase.invoke(
                    target = actionRepository.getAction(actionStatusWrapper.newId).target,
                    attacker = (getStatus(actionStatusWrapper) as StatusData<StatusType.Player>),
                    damageType = DamageType.AtkMultiple(1),
                )
            }

            ActionType.Skill -> {
                skillAction(
                    id = actionStatusWrapper.newId,
                    statusData = getStatus(actionStatusWrapper) as StatusData<StatusType.Player>,
                    actionData = actionRepository.getAction(actionStatusWrapper.newId),
                    statusList = battleInfoRepository.getStatusList(),
                    attackUseCase = attackFromPlayerUseCase,
                    conditionUseCase = conditionFromPlayerUseCase,
                    updateAllyParameter = updatePlayerParameter,
                    updateEnemyParameter = updateEnemyParameter,
                )
            }

            ActionType.TOOL -> {
                toolAction(
                    userId = actionStatusWrapper.newId,
                    actionData = actionRepository.getAction(
                        actionStatusWrapper.newId,
                    ),
                )
            }

            ActionType.None -> Unit
        }
    }

    private suspend fun enemyAction() {
        actionStatusWrapper.apply {
            skillAction(
                id = newId,
                statusData = getStatus(actionStatusWrapper) as StatusData<StatusType.Enemy>,
                statusList = playerStatusRepository.getStatusList(),
                actionData = actionData,
                attackUseCase = attackFromEnemyUseCase,
                conditionUseCase = conditionFromEnemyUseCase,
                updateAllyParameter = updateEnemyParameter,
                updateEnemyParameter = updatePlayerParameter,
            )
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

    private suspend fun <A : StatusType, E : StatusType> skillAction(
        id: Int,
        statusData: StatusData<A>,
        actionData: ActionData,
        statusList: List<Character<E>>,
        attackUseCase: AttackUseCase<A>,
        conditionUseCase: ConditionUseCase,
        updateAllyParameter: UpdateStatusUseCase<A>,
        updateEnemyParameter: UpdateStatusUseCase<E>,
    ) {
        val skill = skillRepository.getItem(
            id = actionData.skillId
        )

        //コスト処理
        when (val costType = skill.costType) {
            is CostType.MP -> {
                updateAllyParameter.decMP(
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
        when (skill as EffectKind) {
            is AttackEffect -> {
                val targetList = findActiveTargetUseCase.invoke(
                    statusList = statusList.map { it.statusData },
                    target = actionData.target,
                    targetNum = skill.targetNum,
                )

                //　複数の対象攻撃
                targetList.forEach {
                    attackUseCase.invoke(
                        target = it,
                        attacker = statusData,
                        damageType = (skill as AttackEffect).damageType,
                    )
                }
            }

            is ConditionEffect -> {
                val targetList = findActiveTargetUseCase.invoke(
                    statusList = statusList.map { it.statusData },
                    target = actionData.target,
                    targetNum = skill.targetNum
                )
                //　複数の対象攻撃
                targetList.forEach {
                    conditionUseCase.invoke(
                        target = it,
                        conditionType = (skill as ConditionEffect).conditionType,
                    )
                }
            }

            is HealEffect -> {
                val target = actionData.ally
                updateAllyParameter.incHP(
                    id = target,
                    amount = (skill as HealEffect).healAmount,
                )
            }

            is BufEffect -> {
                when (skill.targetType) {
                    TargetType.Ally -> {
                        val target = actionData.ally
                        updateAllyParameter.addBuf(
                            id = target,
                            buf = (skill as BufEffect)
                        )
                    }

                    TargetType.Enemy -> {
                        val target = actionData.target
                        updateEnemyParameter.addBuf(
                            id = target,
                            buf = (skill as BufEffect)
                        )
                    }
                }
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

            when (actionStatusWrapper.statusType) {
                StatusType.Player -> {
                    //　playerを確認

                    val statusData = statusDataRepository.getStatusData(
                        id = actionStatusWrapper.newId,
                    )
                    if (statusData.isActive.not()) {
                        continue
                    }

                    val action = actionRepository.getAction(playerId = actionStatusWrapper.newId)
                    val actionType = action.thisTurnAction
                    if (actionType == ActionType.None) {
                        continue
                    }
                }

                StatusType.Enemy -> {
                    val monsterId = actionStatusWrapper.newId
                    val monster = battleInfoRepository
                        .getStatus(id = monsterId)

                    //　monsterを確認
                    if (monster.statusData.isActive.not()) {
                        continue
                    }
                }

                StatusType.None -> throw RuntimeException("Noneで処理はない")
            }

            // 行動可能なのでデータ更新
            mutableAttackingStatusId.value = actionStatusWrapper
            actionState.value = ActionState.Start
            changeActionPhase()
            break
        }
    }

    private fun changeActionPhase() {

        // fixme getNextStateの引数にstatusを追加して、その中で処理したい
        // ステータスに関連する処理も内部で行うようにする
        if (getStatus(actionStatusWrapper).isActive.not()) {
            // 倒れていたらnextに変更
            actionState.value = ActionState.Next
            return
        }

        // 状態の切り替え
        val nextState = actionState.value.getNextState(
            conditionList = getStatus(actionStatusWrapper)
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
                    when (actionStatusWrapper.statusType) {
                        StatusType.Player -> playerAction()
                        StatusType.Enemy -> enemyAction()
                        StatusType.None -> throw RuntimeException("Noneで処理はない")
                    }
                    spendTurn()
                }

                is ActionState.CureParalyze,
                is ActionState.CurePoison,
                    -> {
                    val cured = (nextState as ActionState.Cure).list

                    when (actionStatusWrapper.statusType) {
                        StatusType.Player -> updatePlayerParameter
                        StatusType.Enemy -> updateEnemyParameter
                        StatusType.None -> throw RuntimeException("Noneで処理はない")
                    }.updateConditionList(
                        id = actionStatusWrapper.newId,
                        conditionList = cured,
                    )
                }

                ActionState.Next -> {
                    // アクションをしてもまだ戦闘中なら次のキャラへ
                    if (this@ActionPhaseViewModel.commandRepository.nowBattleCommandType !is FinishCommand) {
                        changeToNextCharacter()
                    }
                }

                ActionState.Paralyze -> Unit
                is ActionState.Poison -> {
                    actionStatusWrapper.apply {
                        when (statusType) {
                            StatusType.Player -> updatePlayerParameter

                            StatusType.Enemy -> updateEnemyParameter
                            StatusType.None -> throw RuntimeException("Noneで処理はない")
                        }.decHP(
                            id = newId,
                            amount = nextState.damage,
                        )
                    }
                }

                ActionState.Start -> Unit
            }
        }
    }

    private fun initAction() {
        resetAttackingPlayer()
        commandRepository.init()
    }

    private fun resetAttackingPlayer() {
        mutableAttackingStatusId.value = dummyStatus
        attackingNumber = NONE_PLAYER
    }

    private suspend fun spendTurn() {
        when (actionStatusWrapper.statusType) {
            StatusType.Player -> updatePlayerParameter
            StatusType.Enemy -> updateEnemyParameter
            StatusType.None -> TODO()
        }.spendTurn(
            id = actionStatusWrapper.newId,
        )
    }

    override fun pressB() {
        pressA()
    }

    companion object {
        private const val NONE_PLAYER = -1
        private val dummyStatus = StatusWrapper(
            newId = -1,
            statusType = StatusType.None,
            status = PlayerStatus(
                statusData = StatusData(name = ""),
                toolList = listOf(),
                skillList = listOf(),
                exp = EXP(listOf()),
            ),
            actionData = ActionData(),

            )
    }
}
