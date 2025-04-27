package gamescreen.map.usecase.battlestart

import core.domain.BattleEventCallback
import core.domain.BattleResult
import core.domain.status.monster.MonsterStatus
import core.repository.battlemonster.BattleInfoRepository
import core.repository.event.EventRepository
import core.repository.screentype.ScreenTypeRepository
import gamescreen.GameScreenType
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.repository.attackeffect.AttackEffectRepository
import gamescreen.battle.repository.commandstate.CommandStateRepository
import gamescreen.battle.repository.flash.FlashRepository
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
    private val flashRepository: FlashRepository,
    private val attackEffectRepository: AttackEffectRepository,
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
            flashRepository.monsterNum = monsterList.size
            attackEffectRepository.monsterNum = monsterList.size

            battleInfoRepository.setBackgroundType(
                backgroundType
            )
            screenTypeRepository.setScreenType(
                gameScreenType = GameScreenType.BATTLE,
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
