package data.usecase.battledecidemonster

import core.domain.mapcell.CellType
import core.domain.status.StatusData
import core.domain.status.monster.MonsterStatus
import data.repository.monster.MonsterRepository
import gamescreen.map.domain.background.BackgroundCell


class DecideBattleMonsterUseCaseImpl(
    private val monsterRepository: MonsterRepository,
) : DecideBattleMonsterUseCase {
    override fun invoke(
        backgroundCell: BackgroundCell,
    ): List<Pair<MonsterStatus, StatusData>> {
        val cellType = backgroundCell.cellType as? CellType.MonsterCell
            ?: return emptyList()

        return when (cellType) {
            CellType.Glass -> List(
                5,
            ) {
                monsterRepository.getMonster(1)
            }

            CellType.Road -> List(
                2,
            ) {
                monsterRepository.getMonster(1)
            }

            CellType.Sand -> List(
                1,
            ) {
                monsterRepository.getMonster(1)
            }
        }
    }
}
