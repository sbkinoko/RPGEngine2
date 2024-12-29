package gamescreen.map.usecase.startbattle

import core.domain.ScreenType
import core.repository.battlemonster.BattleMonsterRepository
import core.repository.screentype.ScreenTypeRepository
import data.monster.MonsterRepository
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.repository.commandstate.CommandStateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.random.Random

class StartBattleUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
    private val screenTypeRepository: ScreenTypeRepository,
    private val commandStateRepository: CommandStateRepository,
    private val actionRepository: ActionRepository,
    private val monsterRepository: MonsterRepository,
) : StartBattleUseCase, KoinComponent {

    override operator fun invoke() {
        // fixme マスによって出現モンスターを変える
        // ランダムで1~5の敵を作成
        val monsterList = List(
            Random.nextInt(5) + 1,
        ) {
            monsterRepository.getMonster(1)
        }

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
