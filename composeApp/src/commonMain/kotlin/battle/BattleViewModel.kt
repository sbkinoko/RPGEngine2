package battle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import battle.domain.BattleCommandType
import battle.repository.battlemonster.BattleMonsterRepository
import battle.repository.commandstate.CommandStateRepository
import battle.usecase.convertscreentypetocontroller.GetControllerByCommandTypeUseCase
import common.Timer
import common.values.playerNum
import controller.domain.ControllerCallback
import controller.domain.StickPosition
import core.domain.ScreenType
import core.domain.status.MonsterStatus
import core.domain.status.PlayerStatus
import core.repository.player.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import main.screentype.ScreenTypeRepository
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

    private val getControllerByCommandTypeUseCase: GetControllerByCommandTypeUseCase by inject()

    private val childViewModel: ControllerCallback?
        get() = getControllerByCommandTypeUseCase.invoke()

    @Composable
    fun CommandStateFlow(): State<BattleCommandType> {
        return commandStateRepository.commandTypeFlow.collectAsState(
            commandStateRepository.nowCommandType
        )
    }

    @Composable
    fun PlayerStatusFlow(): State<List<PlayerStatus>> {
        return playerRepository.getFlowAsState()
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
            playerRepository.getStatus(id = it)
        }
    }

    fun setMonsters(monsters: List<MonsterStatus>) {
        CoroutineScope(Dispatchers.Default).launch {
            battleMonsterRepository.setMonsters(monsters.toMutableList())
        }
    }

    fun reloadMonster() {
        CoroutineScope(Dispatchers.IO).launch {
            battleMonsterRepository.reload()
        }
    }

    fun finishBattle() {
        //　todo useCaseにする
        screenTypeRepository.screenType = ScreenType.FIELD
    }

    private val timer: Timer = Timer(200)

    override fun moveStick(stickPosition: StickPosition) {
        timer.callbackIfTimePassed {
            childViewModel?.moveStick(stickPosition)
        }
    }

    override fun pressA() {
        childViewModel?.pressA()
    }

    override fun pressB() {
        childViewModel?.pressB()
    }

    override fun pressM() {}
}
