package gamescreen.battle.usecase.getdroptool

import core.repository.battlemonster.BattleMonsterRepository
import kotlin.random.Random

class GetDropToolUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
) : GetDropToolUseCase {

    override fun invoke(): Int? {
        battleMonsterRepository.getMonsters().map { monster ->
            monster.dropInfoList.map {
                val rnd = Random.nextInt(100)
                if (rnd < it.probability) {
                    return it.itemId
                }
            }
        }

        return null
    }
}
