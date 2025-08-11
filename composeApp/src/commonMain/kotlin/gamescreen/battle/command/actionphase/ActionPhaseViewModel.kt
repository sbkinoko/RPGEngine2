package gamescreen.battle.command.actionphase

import androidx.compose.runtime.mutableStateOf
import common.DefaultScope
import core.UpdateEnemyUseCaseName
import core.UpdatePlayer
import core.domain.BattleResult
import core.domain.item.AttackEffect
import core.domain.item.BufEffect
import core.domain.item.ConditionEffect
import core.domain.item.CostType
import core.domain.item.DamageType
import core.domain.item.FlyEffect
import core.domain.item.HealEffect
import core.domain.item.Item
import core.domain.item.NeedTarget
import core.domain.item.TargetType
import core.domain.item.UsableItem
import core.domain.status.StatusData
import core.domain.status.StatusType
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.character.battlemonster.BattleInfoRepository
import core.repository.character.player.PlayerCharacterRepository
import core.repository.character.statusdata.StatusDataRepository
import core.usecase.item.useitem.UseItemUseCase
import core.usecase.updateparameter.UpdateStatusUseCase
import data.repository.item.skill.SkillId
import data.repository.item.skill.SkillRepository
import data.repository.item.tool.ToolRepository
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
import gamescreen.battle.usecase.effect.EffectUseCase
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

    private val statusDataRepository: StatusDataRepository,
    private val enemyDataRepository: StatusDataRepository,

    private val useToolUseCase: UseItemUseCase,
    private val effectUseCase: EffectUseCase,
) : BattleChildViewModel<Int>() {
    private val actionRepository: ActionRepository by inject()
    private val battleInfoRepository: BattleInfoRepository by inject()

    private val playerStatusRepository: PlayerCharacterRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val toolRepository: ToolRepository by inject()

    private val updatePlayerParameter: UpdateStatusUseCase by inject(
        qualifier = named(UpdatePlayer)
    )
    private val updateEnemyParameter: UpdateStatusUseCase by inject(
        qualifier = UpdateEnemyUseCaseName,
    )

    private val attackFromPlayerUseCase: AttackUseCase by inject(
        qualifier = named(
            QualifierAttackFromPlayer
        )
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
            enemyDataRepository.getStatusList()
        )

    private val isPlayerAnnihilated: Boolean
        get() = isAnnihilationService(
            statusDataRepository.getStatusList()
        )

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        // 使わないので適当
        SelectManager(
            width = 1,
            itemNum = 1,
        )
    )

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
        get() = enemyDataRepository.getStatusList().size

    private lateinit var speedList: List<StatusWrapper>

    private val actionStatusWrapper: StatusWrapper
        get() = speedList[attackingNumber]

    val actionState =
        mutableStateOf<ActionState>(ActionState.Start)

    private var statusWrapperList: List<StatusWrapper> = emptyList()

    /**
     * statusWrapperに対応したstatusを取得
     */
    private fun StatusWrapper.toStatus(): StatusData {
        // statusWrapperに最新の情報を保持することは出来ない
        return when (statusType) {
            StatusType.Player -> statusDataRepository
            StatusType.Enemy -> enemyDataRepository
            StatusType.None -> throw RuntimeException("Noneで処理はない")
        }.getStatusData(
            newId
        )
    }

    fun init() {
        val list = mutableListOf<StatusWrapper>()

        statusDataRepository.getStatusList()
            .mapIndexed { id, status ->
                list += StatusWrapper(
                    status = status,
                    actionData = actionRepository.getAction(playerId = id),
                    statusType = StatusType.Player,
                    newId = id,
                )
            }

        for (index: Int in 0 until enemyDataRepository.getStatusList().size) {
            val statusData = enemyDataRepository.getStatusData(index)
            val enemyData = battleInfoRepository.getStatus(index)

            val action = decideMonsterActionService.getAction(
                monster = enemyData,
                statusData = statusData,
                playerStatusList = playerStatusRepository.getStatusList(),
            )

            list += StatusWrapper(
                status = statusData,
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

        val actionStatusName = statusWrapper
            .toStatus()
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
        val allyRepository: StatusDataRepository
        val enemyRepository: StatusDataRepository

        when (statusWrapper.statusType) {
            StatusType.Enemy -> {
                allyRepository = enemyDataRepository
                enemyRepository = statusDataRepository
            }

            StatusType.Player -> {
                allyRepository = statusDataRepository
                enemyRepository = enemyDataRepository
            }

            StatusType.None -> throw NotImplementedError()
        }

        val action = statusWrapper.actionData
        val item = getActionItem(statusWrapper = statusWrapper)

        return when ((item as NeedTarget).targetType) {
            TargetType.Enemy,
                -> {
                var targetId = action.target
                if (enemyRepository.getStatusData(targetId)
                        .isActive.not()
                ) {
                    targetId = findActiveTargetUseCase.invoke(
                        statusList = enemyRepository.getStatusList(),
                        target = targetId,
                        targetNum = item.targetNum,
                    ).first()
                }
                enemyRepository.getStatusData(targetId).name
            }

            TargetType.Ally -> {
                val targetId = action.ally
                allyRepository.getStatusData(targetId).name
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

        val attackEffect: (Int) -> Unit = {
            effectUseCase.invoke(it)
        }
        when (actionType) {
            ActionType.Normal -> {
                //　攻撃
                attackFromPlayerUseCase.invoke(
                    target = actionRepository.getAction(actionStatusWrapper.newId).target,
                    attacker = actionStatusWrapper.toStatus(),
                    damageType = DamageType.AtkMultiple(1),
                    effect = attackEffect,
                )
            }

            ActionType.Skill -> {
                skillAction(
                    id = actionStatusWrapper.newId,
                    statusData = actionStatusWrapper.toStatus(),
                    actionData = actionRepository.getAction(actionStatusWrapper.newId),
                    statusList = enemyDataRepository.getStatusList(),
                    attackUseCase = attackFromPlayerUseCase,
                    conditionUseCase = conditionFromPlayerUseCase,
                    updateAllyParameter = updatePlayerParameter,
                    updateEnemyParameter = updateEnemyParameter,
                    attackEffect = attackEffect,
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
                statusData = actionStatusWrapper.toStatus(),
                statusList = statusDataRepository.getStatusList(),
                actionData = actionData,
                attackUseCase = attackFromEnemyUseCase,
                conditionUseCase = conditionFromEnemyUseCase,
                updateAllyParameter = updateEnemyParameter,
                updateEnemyParameter = updatePlayerParameter,
                attackEffect = {},
            )
        }
    }

    private fun checkBattleFinish() {
        // 同時に全滅したら負け

        // プレイヤーが全滅していたらバトル終了
        if (isPlayerAnnihilated) {
            this.commandRepository.push(
                FinishCommand(
                    battleResult = BattleResult.Lose
                )
            )
            return
        }

        // 敵を倒していたらバトル終了
        if (isMonsterAnnihilated) {
            this.commandRepository.push(
                FinishCommand(
                    battleResult = BattleResult.Win
                )
            )
            return
        }
    }

    private suspend fun skillAction(
        id: Int,
        statusData: StatusData,
        actionData: ActionData,
        statusList: List<StatusData>,
        attackUseCase: AttackUseCase,
        conditionUseCase: ConditionUseCase,
        updateAllyParameter: UpdateStatusUseCase,
        updateEnemyParameter: UpdateStatusUseCase,
        attackEffect: (Int) -> Unit,
    ) {
        val skill = skillRepository.getItem(
            id = actionData.skillId
        )

        //コスト処理
        when (val costType = (skill as UsableItem).costType) {
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
        when (skill) {
            is AttackEffect -> {
                val targetList = findActiveTargetUseCase.invoke(
                    statusList = statusList,
                    target = actionData.target,
                    targetNum = (skill as NeedTarget).targetNum,
                )

                //　複数の対象攻撃
                targetList.forEach {
                    attackUseCase.invoke(
                        target = it,
                        attacker = statusData,
                        damageType = (skill as AttackEffect).damageType,
                    ) {
                        attackEffect.invoke(it)
                    }
                }
            }

            is ConditionEffect -> {
                val targetList = findActiveTargetUseCase.invoke(
                    statusList = statusList,
                    target = actionData.target,
                    targetNum = (skill as NeedTarget).targetNum,
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
                when ((skill as NeedTarget).targetType) {
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

            is FlyEffect -> throw RuntimeException()
        }
    }

    private suspend fun toolAction(
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

            // このターンの行動がない場合は次へ
            if (actionStatusWrapper.actionData.thisTurnAction == ActionType.None) {
                continue
            }

            val status = actionStatusWrapper.toStatus()

            // HPがない場合は次へ
            if (status.isActive.not()) {
                continue
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
        if (actionStatusWrapper.toStatus().isActive.not()) {
            // 倒れていたらnextに変更
            actionState.value = ActionState.Next
            return
        }

        // 状態の切り替え
        val nextState = actionState.value.getNextState(
            conditionList = actionStatusWrapper.toStatus().conditionList
        )
        actionState.value = nextState

        // 切り替え後の状態で処理を実行
        DefaultScope.launch {
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
            status = StatusData(name = ""),
            actionData = ActionData(),
        )
    }
}
