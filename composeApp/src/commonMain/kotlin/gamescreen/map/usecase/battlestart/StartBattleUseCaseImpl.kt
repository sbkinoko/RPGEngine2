package gamescreen.map.usecase.battlestart

import core.domain.BattleEventCallback
import core.domain.BattleResult
import core.domain.ScreenType
import core.domain.status.monster.MonsterStatus
import core.repository.battlemonster.BattleInfoRepository
import core.repository.event.EventRepository
import core.repository.screentype.ScreenTypeRepository
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.repository.commandstate.CommandStateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class StartBattleUseCaseImpl(
    private val battleInfoRepository: BattleInfoRepository,
    private val screenTypeRepository: ScreenTypeRepository,
    private val commandStateRepository: CommandStateRepository,
    private val actionRepository: ActionRepository,
    private val eventRepository: EventRepository,
) : StartBattleUseCase, KoinComponent {

    override operator fun invoke(
        monsterList: List<MonsterStatus>,
        battleEventCallback: BattleEventCallback,
        backgroundType: BattleBackgroundType,
    ) {
        if (monsterList.isEmpty()) {
            return
        }

        CoroutineScope(Dispatchers.Default).launch {
            battleInfoRepository.setMonsters(
                monsterList
            )
            battleInfoRepository.setBackgroundType(
                backgroundType
            )
            screenTypeRepository.setScreenType(
                screenType = ScreenType.BATTLE,
            )
            commandStateRepository.init()
            actionRepository.resetTarget()
            eventRepository.setResult(BattleResult.None)
            eventRepository.setCallBack(
                battleEventCallback,
            )
        }
    }
}
