package gamescreen.battle

import controller.domain.ControllerCallback
import controller.domain.Stick
import core.domain.status.StatusData
import core.domain.status.monster.MonsterStatus
import core.repository.battlemonster.BattleInfoRepository
import core.repository.statusdata.StatusDataRepository
import gamescreen.battle.repository.attackeffect.AttackEffectRepository
import gamescreen.battle.repository.commandstate.CommandStateRepository
import gamescreen.battle.repository.flash.FlashRepository
import gamescreen.battle.usecase.getcontrollerbyscreentype.GetControllerByCommandTypeUseCase
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BattleViewModel(
    flashRepository: FlashRepository,
    attackEffectInfoRepository: AttackEffectRepository,

    statusDataRepository: StatusDataRepository,
) :
    ControllerCallback,
    KoinComponent {

    private val battleInfoRepository: BattleInfoRepository by inject()
    private val commandStateRepository: CommandStateRepository by inject()

    private val getControllerByCommandTypeUseCase: GetControllerByCommandTypeUseCase by inject()

    private val childController: ControllerCallback
        get() = getControllerByCommandTypeUseCase.invoke()

    val commandStateFlow =
        commandStateRepository.commandStateFlow

    val statusDataFlow: StateFlow<List<StatusData>> =
        statusDataRepository.statusDataFlow

    val monsterStatusFlow: StateFlow<List<MonsterStatus>> =
        battleInfoRepository.monsterListStateFLow

    val battleBackgroundTypeFlow =
        battleInfoRepository.backgroundType

    val flashStateFlow = flashRepository.flashStateFlow

    val attackEffectState = attackEffectInfoRepository.effectStateFlow

    fun reloadMonster() {
        battleInfoRepository.reload()
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
