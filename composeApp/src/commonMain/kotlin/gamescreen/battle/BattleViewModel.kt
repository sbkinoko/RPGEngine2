package gamescreen.battle

import controller.domain.ControllerCallback
import controller.domain.Stick
import core.domain.status.MonsterStatus
import core.domain.status.PlayerStatus
import core.repository.battlemonster.BattleMonsterRepository
import core.repository.player.PlayerStatusRepository
import gamescreen.battle.repository.commandstate.CommandStateRepository
import gamescreen.battle.usecase.getcontrollerbyscreentype.GetControllerByCommandTypeUseCase
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class BattleViewModel :
    ControllerCallback,
    KoinComponent {

    lateinit var players: List<PlayerStatus>

    private val playerStatusRepository: PlayerStatusRepository by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()
    private val commandStateRepository: CommandStateRepository by inject()

    private val getControllerByCommandTypeUseCase: GetControllerByCommandTypeUseCase by inject()

    private val childController: ControllerCallback
        get() = getControllerByCommandTypeUseCase.invoke()

    val commandStateFlow =
        commandStateRepository.commandStateFlow

    val playerStatusFlow: StateFlow<List<PlayerStatus>> = playerStatusRepository.playerStatusFlow

    val monsterStatusFlow: StateFlow<List<MonsterStatus>> =
        battleMonsterRepository.monsterListStateFLow

    init {
        initPlayers()
    }

    private fun initPlayers() {
        players = List(Constants.playerNum) {
            playerStatusRepository.getStatus(id = it)
        }
    }

    fun reloadMonster() {
        battleMonsterRepository.reload()
    }

    override fun moveStick(stick: Stick) {
        childController.moveStick(stick)
    }

    override fun pressA() {
        childController.pressA()
    }

    override fun pressB() {
        childController.pressB()
    }

    override fun pressM() {}
}
