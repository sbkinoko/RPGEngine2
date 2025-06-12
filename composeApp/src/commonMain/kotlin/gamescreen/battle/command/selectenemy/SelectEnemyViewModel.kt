package gamescreen.battle.command.selectenemy

import controller.domain.ArrowCommand
import controller.domain.Stick
import core.domain.status.monster.MonsterStatus
import core.repository.battlemonster.BattleInfoRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.domain.SelectedEnemyState
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.service.findtarget.FindTargetService
import gamescreen.battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import gamescreen.battle.usecase.findactivetarget.FindActiveTargetUseCase
import gamescreen.battle.usecase.gettargetnum.GetTargetNumUseCase
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class SelectEnemyViewModel : BattleChildViewModel() {
    private val monsterRepository: BattleInfoRepository by inject()
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
            val command = commandRepository.nowBattleCommandType as SelectEnemyCommand
            return command.playerId
        }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            commandRepository.commandStateFlow.collect {
                updateArrow()
            }
        }
    }

    private val monsters: List<MonsterStatus>
        get() = monsterRepository.getStatusList()

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

    // 使わないので適当
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    override fun moveStick(stick: Stick) {
        val target = selectedEnemyState.value.selectedEnemy.first()

        val newTarget = when (stick.toCommand()) {
            ArrowCommand.Right -> findTargetService.findNext(
                target = target,
                statusList = monsters.map { it.statusData },
            )

            ArrowCommand.Left -> findTargetService.findPrev(
                target = target,
                statusList = monsters.map { it.statusData },
            )

            else -> return
        }

        setTargetEnemy(newTarget)
    }

    private fun setTargetEnemy(target: Int) {
        val targetList = findActiveTargetUseCase(
            target = target,
            targetNum = getTargetNumUseCase(playerId = playerId),
            statusList = monsters.map { it.statusData },
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
            commandRepository.nowBattleCommandType as? SelectEnemyCommand

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
