package battle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import battle.command.actionphase.ActionPhaseViewModel
import battle.command.escape.EscapeViewModel
import battle.command.main.MainViewModel
import battle.command.playeraction.PlayerActionViewModel
import battle.command.selectenemy.SelectEnemyViewModel
import battle.domain.AttackPhaseCommand
import battle.domain.CommandType
import battle.domain.EscapeCommand
import battle.domain.FinishCommand
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.repository.battlemonster.BattleMonsterRepository
import battle.repository.commandstate.CommandStateRepository
import common.Timer
import common.repository.player.PlayerRepository
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

    private val playerRepository: PlayerRepository by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()
    private val commandStateRepository: CommandStateRepository by inject()

    private val screenTypeRepository: ScreenTypeRepository by inject()

    val mainViewModel = MainViewModel()
    val playerActionViewModel = PlayerActionViewModel()
    val selectEnemyViewModel = SelectEnemyViewModel()
    val actionPhaseViewModel = ActionPhaseViewModel()
    val escapeViewModel = EscapeViewModel()

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

    fun finishBattle() {
        screenTypeRepository.screenType = ScreenType.FIELD
    }

    //todo finishViewModelを作ったらnullableをやめる
    private fun CommandType.toViewModel(): ControllerCallback? {
        return when (this) {
            is MainCommand -> mainViewModel
            is PlayerActionCommand -> playerActionViewModel
            is SelectEnemyCommand -> selectEnemyViewModel
            is AttackPhaseCommand -> actionPhaseViewModel
            is EscapeCommand -> escapeViewModel
            is FinishCommand -> null
        }
    }

    private val timer: Timer = Timer(200)

    override fun moveStick(stickPosition: StickPosition) {
        timer.callbackIfTimePassed {
            commandStateRepository.nowCommandType
                .toViewModel()?.moveStick(stickPosition)
        }
    }

    override fun pressA() {
        commandStateRepository.nowCommandType.toViewModel()?.pressA()
    }

    override fun pressB() {
        commandStateRepository.nowCommandType.toViewModel()?.pressB()
    }

    override fun pressM() {}

}
