package gamescreen.map.usecase.startbattle

import core.domain.ScreenType
import core.domain.status.MonsterStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
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

    override operator fun invoke() {
        // ランダムで1~5の敵を作成
        val monsterList = List(
            5
            //    Random.nextInt(5) + 1,
        ) {
            MonsterStatus(
                1, "花",
                hp = HP(
                    maxValue = 10,
                ),
                mp = MP(
                    maxValue = 10,
                )
            )
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
