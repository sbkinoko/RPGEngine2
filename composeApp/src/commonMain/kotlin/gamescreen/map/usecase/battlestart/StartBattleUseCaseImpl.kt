package gamescreen.map.usecase.battlestart

import core.domain.ScreenType
import core.domain.status.MonsterStatus
import core.repository.battlemonster.BattleMonsterRepository
import core.repository.screentype.ScreenTypeRepository
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.repository.commandstate.CommandStateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class StartBattleUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
    private val screenTypeRepository: ScreenTypeRepository,
    private val commandStateRepository: CommandStateRepository,
    private val actionRepository: ActionRepository,
) : StartBattleUseCase, KoinComponent {

    override operator fun invoke(
        monsterList: List<MonsterStatus>,
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            battleMonsterRepository.setMonsters(
                monsterList
            )

            screenTypeRepository.screenType = ScreenType.BATTLE
            commandStateRepository.init()
            actionRepository.resetTarget()
        }
    }
}
