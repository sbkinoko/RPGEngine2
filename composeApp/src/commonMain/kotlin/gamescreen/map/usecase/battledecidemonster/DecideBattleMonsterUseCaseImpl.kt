package gamescreen.map.usecase.battledecidemonster

import core.domain.mapcell.CellType
import core.domain.status.monster.MonsterStatus
import data.monster.MonsterRepository
import gamescreen.map.domain.background.BackgroundCell

// fixme dataに移動する
class DecideBattleMonsterUseCaseImpl(
    private val monsterRepository: MonsterRepository,
) : DecideBattleMonsterUseCase {
    override fun invoke(
        backgroundCell: BackgroundCell,
    ): List<MonsterStatus> {
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
        }
    }
}
