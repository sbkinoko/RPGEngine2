package gamescreen.battle.command.selectenemy

import common.DefaultScope
import core.domain.status.StatusData
import core.repository.statusdata.StatusDataRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.domain.SelectedEnemyState
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import gamescreen.battle.usecase.findactivetarget.FindActiveTargetUseCase
import gamescreen.battle.usecase.gettargetnum.GetTargetNumUseCase
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class SelectEnemyViewModel(
    private val enemyStatusDataRepository: StatusDataRepository,
) : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()

    private val findActiveTargetUseCase: FindActiveTargetUseCase by inject()
    private val getTargetNumUseCase: GetTargetNumUseCase by inject()
    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()

    private var mutableSelectedEnemyState: MutableStateFlow<SelectedEnemyState> =
        MutableStateFlow(SelectedEnemyState(emptyList()))
    val selectedEnemyState: StateFlow<SelectedEnemyState> =
        mutableSelectedEnemyState.asStateFlow()

    val playerId: Int
        get() {
            val command = commandRepository.nowBattleCommandType as SelectEnemyCommand
            return command.playerId
        }

    // 使わないので適当
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    private val monsters: List<StatusData>
        get() = enemyStatusDataRepository.getStatusList()

    private var job = DefaultScope.launch { }

    init {
        DefaultScope.launch {
            commandRepository.commandStateFlow.collect {
                updateArrow()
            }
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is SelectEnemyCommand
    }

    override fun goNextImpl() {
        // ターゲットを保存
        actionRepository.setTarget(
            playerId = playerId,
            // 表示ようにリストになっていたが、保存する際は先頭だけ保存して、
            //　実際に攻撃するときに攻撃対象を再計算する
            target = mutableSelectedEnemyState.value.selectedEnemy.first(),
        )

        changeSelectingActionPlayerUseCase.invoke()
    }

    override fun selectable(): Boolean {
        val target = selectManager.selected
        return enemyStatusDataRepository.getStatusData(target).isActive
    }

    fun selectAttackMonster(monsterId: Int) {
        //　敵を選択中以外は操作しない
        if (selectedEnemyState.value.selectedEnemy.isEmpty()) {
            return
        }

        // すでに選んでる敵を選んだら確定
        if (selectManager.selected == monsterId) {
            pressA()
            return
        }

        selectManager.selected = monsterId
    }

    fun updateManager() {
        job.cancel()

        selectManager = SelectManager(
            width = enemyStatusDataRepository.getStatusList().size,
            itemNum = enemyStatusDataRepository.getStatusList().size,
        )

        job = DefaultScope.launch {
            // 選択の先頭が変わった
            selectManager.selectedFlowState.collect {
                val targetList = findActiveTargetUseCase(
                    target = it,
                    targetNum = getTargetNumUseCase.invoke(playerId = playerId),
                    statusList = monsters,
                )

                mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
                    selectedEnemy = targetList,
                )
            }
        }

        job.start()
    }

    fun updateArrow() {
        val command =
            commandRepository.nowBattleCommandType as? SelectEnemyCommand

        if (command == null) {
            mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
                selectedEnemy = emptyList(),
            )
            return
        }

        val playerId = command.playerId

        val action = actionRepository.getAction(playerId)

        selectManager.selected = action.target
    }
}
