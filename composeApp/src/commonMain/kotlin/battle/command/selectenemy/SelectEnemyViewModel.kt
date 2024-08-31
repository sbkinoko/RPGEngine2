package battle.command.selectenemy

import battle.BattleChildViewModel
import battle.domain.ActionType
import battle.domain.AttackPhaseCommand
import battle.domain.CommandType
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SelectedEnemyState
import battle.repository.action.ActionRepository
import battle.repository.battlemonster.BattleMonsterRepository
import battle.service.FindTargetService
import common.status.MonsterStatus
import common.values.playerNum
import controller.domain.ArrowCommand
import controller.domain.StickPosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import menu.domain.SelectManager
import org.koin.core.component.inject

class SelectEnemyViewModel : BattleChildViewModel() {
    private val monsterRepository: BattleMonsterRepository by inject()
    private val actionRepository: ActionRepository by inject()

    private val findTargetService: FindTargetService by inject()

    private var mutableSelectedEnemyState: MutableStateFlow<SelectedEnemyState> =
        MutableStateFlow(SelectedEnemyState(emptyList(), 0))
    val selectedEnemyState: StateFlow<SelectedEnemyState> =
        mutableSelectedEnemyState.asStateFlow()

    override val canBack: Boolean
        get() = true

    init {
        CoroutineScope(Dispatchers.Default).launch {
            commandStateRepository.commandTypeFlow.collect {
                updateArrow()
            }
        }
    }

    private val monsters: List<MonsterStatus>
        get() = monsterRepository.getMonsters()

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is SelectEnemyCommand
    }

    override fun goNextImpl() {
        val command = commandStateRepository.nowCommandType as? SelectEnemyCommand ?: return
        val playerId = command.playerId

        // 行動を保存
        actionRepository.setAction(
            actionType = ActionType.Normal,
            playerId = playerId,
            target = mutableSelectedEnemyState.value.selectedEnemy,
        )

        // 次のコマンドに移動
        if (playerId < playerNum - 1) {
            commandStateRepository.push(
                PlayerActionCommand(
                    playerId = playerId + 1,
                )
            )
        } else {
            //　一周したので攻撃フェーズに移動
            commandStateRepository.push(AttackPhaseCommand)
        }
    }

    // 使わないので適当
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    override fun moveStick(stickPosition: StickPosition) {
        val target = selectedEnemyState.value.selectedEnemy.first()

        val newTarget = when (stickPosition.toCommand()) {
            ArrowCommand.Right -> findFirstRightTarget(target)
            ArrowCommand.Left -> findFirstLeftTarget(target)
            else -> return
        }

        setTargetEnemy(newTarget)
    }

    private fun findFirstRightTarget(
        target: Int,
    ): Int {
        return findTargetService.findNext(
            target = target,
            monsters = monsters,
        )
    }

    private fun findFirstLeftTarget(
        target: Int,
    ): Int {
        return findTargetService.findPrev(
            target = target,
            monsters = monsters,
        )
    }

    private fun setTargetEnemy(target: Int) {
        // fixme 複数選択するようになったら修正
        mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
            selectedEnemy = listOf(target)
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
            commandStateRepository.nowCommandType as? SelectEnemyCommand

        if (command == null) {
            mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
                selectedEnemy = emptyList(),
            )
            return
        }
        val playerId = command.playerId

        val action = actionRepository.getAction(playerId)
        var target = action.target.first()
        // fixme list返すようにする
        while (monsters[target].isActive.not()) {
            target = findFirstRightTarget(target)
        }
        mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
            selectedEnemy = listOf(target),
        )
    }
}
