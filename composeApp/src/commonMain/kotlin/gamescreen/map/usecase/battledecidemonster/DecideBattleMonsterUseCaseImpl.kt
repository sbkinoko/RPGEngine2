package gamescreen.map.usecase.battledecidemonster

import core.domain.status.MonsterStatus
import data.monster.MonsterRepository
import kotlin.random.Random

class DecideBattleMonsterUseCaseImpl(
    private val monsterRepository: MonsterRepository,
) : DecideBattleMonsterUseCase {
    override fun invoke(): List<MonsterStatus> {
        // fixme マスによって出現モンスターを変える
        // ランダムで1~5の敵を作成
        return List(
            Random.nextInt(5) + 1,
        ) {
            monsterRepository.getMonster(1)
        }
    }
}
