package battle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import battle.command.actionphase.ActionPhaseViewModel
import battle.command.escape.EscapeViewModel
import battle.command.main.BattleMainViewModel
import battle.command.playeraction.PlayerActionViewModel
import battle.command.selectally.SelectAllyViewModel
import battle.command.selectenemy.SelectEnemyViewModel
import battle.command.skill.SkillCommandViewModel
import battle.domain.AttackPhaseCommand
import battle.domain.CommandType
import battle.domain.EscapeCommand
import battle.domain.FinishCommand
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectAllyCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
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

    private val battleMainViewModel: BattleMainViewModel by inject()
    private val playerActionViewModel: PlayerActionViewModel by inject()
    private val selectEnemyViewModel: SelectEnemyViewModel by inject()
    private val actionPhaseViewModel: ActionPhaseViewModel by inject()
    private val escapeViewModel: EscapeViewModel by inject()
    private val skillCommandViewModel: SkillCommandViewModel by inject()
    private val selectAllyViewModel: SelectAllyViewModel by inject()

    @Composable
    fun CommandStateFlow(): State<CommandType> {
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
        screenTypeRepository.screenType = ScreenType.FIELD
    }

    //todo 外に専用の関数として取り出す
    //todo finishViewModelを作ったらnullableをやめる
    private fun CommandType.toViewModel(): ControllerCallback? {
        return when (this) {
            is MainCommand -> battleMainViewModel
            is PlayerActionCommand -> playerActionViewModel
            is SelectEnemyCommand -> selectEnemyViewModel
            is SelectAllyCommand -> selectAllyViewModel
            is AttackPhaseCommand -> actionPhaseViewModel
            is EscapeCommand -> escapeViewModel
            is FinishCommand -> null
            is SkillCommand -> skillCommandViewModel
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
