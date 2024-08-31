package battle.command.actionphase

import battle.BattleChildViewModel
import battle.domain.ActionType
import battle.domain.AttackPhaseCommand
import battle.domain.CommandType
import battle.domain.FinishCommand
import battle.repository.action.ActionRepository
import battle.repository.battlemonster.BattleMonsterRepository
import battle.usecase.AttackUseCase
import battle.usecase.IsAllMonsterNotActiveUseCase
import battle.usecase.findactivetarget.FindActiveTargetUseCase
import common.repository.player.PlayerRepository
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
    private val findActiveTargetUseCase: FindActiveTargetUseCase by inject()
    private val isAllMonsterNotActiveUseCase: IsAllMonsterNotActiveUseCase by inject()

    override val canBack: Boolean
        get() = false

    init {
        CoroutineScope(Dispatchers.IO).launch {
            commandStateRepository.commandTypeFlow.collect {
                if (it is AttackPhaseCommand) {
                    mutableAttackingPlayerId.value = 0
                }
            }
        }
    }

    // fixme attackingPlayerは削除する
    // 敵の攻撃が挟まってPlayerだけじゃなくなるから
    private val mutableAttackingPlayerId: MutableStateFlow<Int> = MutableStateFlow(0)
    val attackingPlayerId: StateFlow<Int> = mutableAttackingPlayerId.asStateFlow()

    // 使わないので適当
    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    val targetName: String
        get() {
            val targetId = actionRepository.getAction(attackingPlayerId.value).target
            return battleMonsterRepository.getMonster(targetId).name
        }

    fun getPlayerName(id: Int): String {
        return playerRepository.getPlayer(id).name
    }

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is AttackPhaseCommand
    }

    override fun goNextImpl() {
        CoroutineScope(Dispatchers.IO).launch {
            when (actionRepository.getAction(attackingPlayerId.value).thisTurnAction) {
                ActionType.Normal -> {
                    //　攻撃
                    attackUseCase(
                        target = actionRepository.getAction(attackingPlayerId.value).target,
                        damage = 10,
                    )
                }

                ActionType.Skill -> {
                    // MP減らす
                    val player = playerRepository.getPlayer(attackingPlayerId.value)
                    val afterPlayer = player.copy(
                        mp = player.mp.copy(
                            value = player.mp.value - 1
                        )
                    )
                    playerRepository.setPlayer(
                        id = attackingPlayerId.value,
                        status = afterPlayer,
                    )

                    val targetList = findActiveTargetUseCase(
                        statusList = battleMonsterRepository.getMonsters(),
                        target = actionRepository.getAction(attackingPlayerId.value).target,
                        targetNum = 2,
                    )

                    //　複数の対象攻撃
                    targetList.forEach {
                        attackUseCase(
                            target = it,
                            damage = 10,
                        )
                    }
                }
            }

            // 敵を倒していたらバトル終了
            if (isAllMonsterNotActiveUseCase()) {
                commandStateRepository.push(
                    FinishCommand
                )
                return@launch
            }

            //　次のプレイヤーに移動
            if (attackingPlayerId.value < playerNum - 1) {
                mutableAttackingPlayerId.value++
            } else {
                mutableAttackingPlayerId.value = 0
                commandStateRepository.init()
            }
        }
    }
}
