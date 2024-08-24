package battle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import battle.command.actionphase.ActionPhaseViewModel
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    private val actionRepository: ActionRepository by inject()
    private val playerRepository: PlayerRepository by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()
    private val commandStateRepository: CommandStateRepository by inject()

    private val screenTypeRepository: ScreenTypeRepository by inject()

    val mainViewModel = MainViewModel()
    val playerActionViewModel = PlayerActionViewModel()
    val selectEnemyViewModel = SelectEnemyViewModel()
    val actionPhaseViewModel = ActionPhaseViewModel()

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

    fun reloadMonster() {
        CoroutineScope(Dispatchers.IO).launch {
            battleMonsterRepository.reload()
        }
    }

    fun startBattle() {
        screenTypeRepository.screenType = ScreenType.BATTLE
        commandStateRepository.init()
        actionPhaseViewModel.init()
        actionRepository.resetTarget()
    }

    fun finishBattle() {
        screenTypeRepository.screenType = ScreenType.FIELD
    }

    //todo finishViewModelを作ったらnullableをやめる
    fun CommandType.toViewModel(): ControllerCallback? {
        return when (this) {
            is MainCommand -> mainViewModel
            is PlayerActionCommand -> playerActionViewModel
            is SelectEnemyCommand -> selectEnemyViewModel
            is AttackPhaseCommand -> actionPhaseViewModel
            else -> null
        }
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

    override var pressA: () -> Unit = {
        commandStateRepository.nowCommandType.toViewModel()?.let {
            it.pressA()
        }
    }

    override var pressB: () -> Unit = {
        commandStateRepository.nowCommandType.toViewModel()?.let {
            it.pressB()
        }
    }

    override var pressM: () -> Unit = {}

}
