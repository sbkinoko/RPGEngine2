package battle.command.actionphase

import battle.BattleChildViewModel
import battle.domain.AttackPhaseCommand
import battle.domain.CommandType
import battle.domain.FinishCommand
import battle.repository.action.ActionRepository
import battle.repository.battlemonster.BattleMonsterRepository
import battle.usecase.AttackUseCase
import battle.usecase.IsAllMonsterNotActiveUseCase
import common.repository.PlayerRepository
import common.values.playerNum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import menu.domain.SelectManager
import org.koin.core.component.inject

class ActionPhaseViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()
    private val playerRepository: PlayerRepository by inject()

    private val attackUseCase: AttackUseCase by inject()
    private val isAllMonsterNotActiveUseCase = IsAllMonsterNotActiveUseCase()

    private val mutableAttackingPlayerId: MutableStateFlow<Int> = MutableStateFlow(0)
    val attackingPlayerId: StateFlow<Int> = mutableAttackingPlayerId.asStateFlow()

    val targetName: String
        get() {
            val targetId = actionRepository.getAction(attackingPlayerId.value).target.first()
            return battleMonsterRepository.getMonster(targetId).name
        }

    fun getPlayerName(id: Int): String {
        return playerRepository.getPlayer(attackingPlayerId.value).name
    }

    fun init() {
        mutableAttackingPlayerId.value = 0
    }

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is AttackPhaseCommand
    }

    override fun goNextImpl() {
        CoroutineScope(Dispatchers.IO).launch {
            attack(
                target = actionRepository.getAction(attackingPlayerId.value).target.first(),
                damage = 10,
            )
            if (attackingPlayerId.value < playerNum - 1) {
                mutableAttackingPlayerId.value++
            } else {
                mutableAttackingPlayerId.value = 0
                commandStateRepository.init()
            }
        }
    }

    private suspend fun attack(
        target: Int,
        damage: Int,
    ) {
        attackUseCase(
            target = target,
            damage = damage,
        )

        if (isAllMonsterNotActiveUseCase()) {
            commandStateRepository.push(
                FinishCommand
            )
        }
    }

    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )
}
