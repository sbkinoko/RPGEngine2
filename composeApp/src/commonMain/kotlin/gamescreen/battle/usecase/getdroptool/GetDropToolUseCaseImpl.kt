package gamescreen.battle.usecase.getdroptool

import core.repository.battlemonster.BattleMonsterRepository
import kotlin.random.Random

class GetDropToolUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
) : GetDropToolUseCase {

    override fun invoke(): List<Int> {
        // ドロップアイテムの情報を格納するリスト
        val dropList: MutableList<Int> = mutableListOf()

        battleMonsterRepository.getMonsters().map { monster ->
            monster.dropInfoList.map {
                val rnd = Random.nextInt(100)
                if (rnd < it.probability) {
                    dropList.add(it.itemId)
                }
            }
        }

        return dropList
    }
}
