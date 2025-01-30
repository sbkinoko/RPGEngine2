package data.battle

import core.domain.status.monster.MonsterStatus
import data.monster.MonsterRepository
import gamescreen.battle.domain.BattleId

class BattleDataRepositoryImpl(
    private val monsterRepository: MonsterRepository,
) : BattleDataRepository {
    override fun getBattleMonsterData(battleId: BattleId): List<MonsterStatus> {
        return when (battleId) {
            BattleId.Battle1 -> listOf(
                monsterRepository.getMonster(0)
            )
        }
    }
}
