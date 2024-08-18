package battle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import battle.command.attackphase.AttackPhaseCommandCallBack
import battle.command.main.MainViewModel
import battle.command.playeraction.PlayerActionViewModel
import battle.command.selectenemy.SelectEnemyViewModel
import battle.domain.AttackPhaseCommand
import battle.domain.CommandType
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.repository.action.ActionRepository
import battle.repository.battlemonster.BattleMonsterRepository
import battle.repository.commandstate.CommandStateRepository
import battle.usecase.AttackUseCase
import common.Timer
import common.repository.PlayerRepository
import common.status.MonsterStatus
import common.status.PlayerStatus
import common.values.playerNum
import controller.domain.ControllerCallback
import controller.domain.StickPosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import main.domain.ScreenType
import main.repository.screentype.ScreenTypeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BattleViewModel :
    ControllerCallback,
    KoinComponent {
    var monsters: StateFlow<List<MonsterStatus>>

    lateinit var players: List<PlayerStatus>

    override var pressB: () -> Unit = {
        screenTypeRepository.screenType = ScreenType.FIELD
    }

    override var pressM: () -> Unit = {}

    private val actionRepository: ActionRepository by inject()
    private val playerRepository: PlayerRepository by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()
    private val commandStateRepository: CommandStateRepository by inject()

    private val attackUseCase: AttackUseCase by inject()

    private val screenTypeRepository: ScreenTypeRepository by inject()

    val mainViewModel = MainViewModel()
    val playerActionViewModel = PlayerActionViewModel()
    val selectEnemyViewModel = SelectEnemyViewModel()

    val targetName: String
        get() {
            val targetId = actionRepository.getAction(attackingPlayerId.value).target.first()
            return battleMonsterRepository.getMonster(targetId).name
        }

    /**
     * 敵が全滅したかどうかをチェック
     */
    val isAllMonsterNotActive: Boolean
        get() = !battleMonsterRepository.getMonsters().any {
            it.isActive
        }

    val attackPhaseCommandCallback = object : AttackPhaseCommandCallBack {
        override val pressA: () -> Unit = pressA@{
            if ((commandStateRepository.nowCommandType) !is AttackPhaseCommand) return@pressA
            attackPhase()
        }
    }

    @Composable
    fun CommandStateFlow(): State<CommandType> {
        return commandStateRepository.commandTypeFlow.collectAsState(
            commandStateRepository.nowCommandType
        )
    }

    init {
        initPlayers()
        monsters =
            battleMonsterRepository.monsterListFlow.map {
                it
            }.stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    private fun initPlayers() {
        players = List(playerNum) {
            playerRepository.getPlayer(id = it)
        }
    }

    fun setMonsters(monsters: List<MonsterStatus>) {
        CoroutineScope(Dispatchers.Default).launch {
            battleMonsterRepository.setMonster(monsters.toMutableList())
        }
    }

    suspend fun attack(
        target: Int,
        damage: Int,
    ) {
        attackUseCase(
            target = target,
            damage = damage,
        )

        if (isAllMonsterNotActive) {
            finishBattle()
        }
    }

    fun startBattle() {
        screenTypeRepository.screenType = ScreenType.BATTLE
        commandStateRepository.init()
        mutableAttackingPlayerId.value = 0
        actionRepository.resetTarget()
    }

    private fun finishBattle() {
        pressB()
    }

    private val timer: Timer = Timer(200)

    override fun moveStick(stickPosition: StickPosition) {
        timer.callbackIfTimePassed {
            when (commandStateRepository.nowCommandType) {
                is MainCommand ->
                    mainViewModel.moveStick(stickPosition)

                is PlayerActionCommand ->
                    playerActionViewModel.moveStick(stickPosition)

                is SelectEnemyCommand ->
                    selectEnemyViewModel.moveStick(stickPosition)

                else -> Unit
            }
        }
    }

    override var pressA = {
        when (
            commandStateRepository.nowCommandType
        ) {
            is MainCommand -> {
                mainViewModel.pressA()
            }

            is PlayerActionCommand -> {
                playerActionViewModel.pressA()
            }

            is SelectEnemyCommand -> {
                selectEnemyViewModel.pressA()
            }

            is AttackPhaseCommand -> {
                attackPhase()
            }
        }
    }

    private val mutableAttackingPlayerId: MutableStateFlow<Int> = MutableStateFlow(0)
    val attackingPlayerId: StateFlow<Int> = mutableAttackingPlayerId.asStateFlow()

    private fun attackPhase() {
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

    fun reloadMonster() {
        CoroutineScope(Dispatchers.IO).launch {
            battleMonsterRepository.reload()
        }
    }
}
