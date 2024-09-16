package battle.command.selectenemy

import battle.BattleChildViewModel
import battle.domain.BattleCommandType
import battle.domain.SelectEnemyCommand
import battle.domain.SelectedEnemyState
import battle.repository.action.ActionRepository
import battle.repository.battlemonster.BattleMonsterRepository
import battle.service.FindTargetService
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import battle.usecase.findactivetarget.FindActiveTargetUseCase
import battle.usecase.gettargetnum.GetTargetNumUseCase
import controller.domain.ArrowCommand
import controller.domain.StickPosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import main.status.MonsterStatus
import menu.domain.SelectManager
import org.koin.core.component.inject

class SelectEnemyViewModel : BattleChildViewModel() {
    private val monsterRepository: BattleMonsterRepository by inject()
    private val actionRepository: ActionRepository by inject()

    private val findActiveTargetUseCase: FindActiveTargetUseCase by inject()
    private val getTargetNumUseCase: GetTargetNumUseCase by inject()
    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()

    private val findTargetService: FindTargetService by inject()

    private var mutableSelectedEnemyState: MutableStateFlow<SelectedEnemyState> =
        MutableStateFlow(SelectedEnemyState(emptyList()))
    val selectedEnemyState: StateFlow<SelectedEnemyState> =
        mutableSelectedEnemyState.asStateFlow()

    val playerId: Int
        get() {
            val command = commandStateRepository.nowBattleCommandType as SelectEnemyCommand
            return command.playerId
        }

    override val canBack: Boolean
        get() = true

    init {
        CoroutineScope(Dispatchers.Default).launch {
            commandStateRepository.battleCommandTypeFlow.collect {
                updateArrow()
            }
        }
    }

    private val monsters: List<MonsterStatus>
        get() = monsterRepository.getMonsters()

    override fun isBoundedImpl(battleCommandType: BattleCommandType): Boolean {
        return battleCommandType is SelectEnemyCommand
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

    // 使わないので適当
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    override fun moveStick(stickPosition: StickPosition) {
        val target = selectedEnemyState.value.selectedEnemy.first()

        val newTarget = when (stickPosition.toCommand()) {
            ArrowCommand.Right -> findTargetService.findNext(
                target = target,
                statusList = monsters,
            )

            ArrowCommand.Left -> findTargetService.findPrev(
                target = target,
                statusList = monsters,
            )

            else -> return
        }

        setTargetEnemy(newTarget)
    }

    private fun setTargetEnemy(target: Int) {
        val targetList = findActiveTargetUseCase(
            target = target,
            targetNum = getTargetNumUseCase(playerId = playerId),
            statusList = monsters,
        )

        mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
            selectedEnemy = targetList,
        )
    }

    fun selectAttackMonster(monsterId: Int) {
        //　敵を選択中以外は操作しない
        if (selectedEnemyState.value.selectedEnemy.isEmpty()) {
            return
        }

        // すでに選んでる敵を選んだら確定
        if (selectedEnemyState.value.selectedEnemy.first() == monsterId) {
            pressA()
            return
        }

        // 別の敵を選択
        setTargetEnemy(
            monsterId
        )
    }

    fun updateArrow() {
        val command =
            commandStateRepository.nowBattleCommandType as? SelectEnemyCommand

        if (command == null) {
            mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
                selectedEnemy = emptyList(),
            )
            return
        }
        val playerId = command.playerId

        val action = actionRepository.getAction(playerId)

        setTargetEnemy(
            target = action.target
        )
    }
}
