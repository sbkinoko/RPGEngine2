package gamescreen.battle.usecase.decideactionorder

import core.repository.battlemonster.BattleMonsterRepository
import core.repository.player.PlayerStatusRepository
import values.Constants.Companion.playerNum

class DecideActionOrderUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val battleMonsterRepository: BattleMonsterRepository,
) : DecideActionOrderUseCase {
    override fun invoke(): List<Int> {
        val mutableList = mutableListOf<SpeedInfo>()
        for (id: Int in 0 until playerNum) {
            mutableList += SpeedInfo(
                speed = playerStatusRepository.getStatus(id = id).speed,
                id = id,
            )
        }

        battleMonsterRepository.getMonsters()
            .mapIndexed { index, status ->
                mutableList += SpeedInfo(
                    speed = status.speed,
                    id = index + playerNum
                )
            }

        return mutableList
            // 素早さ順でソート
            .sortedWith(speedComparator)
            // 降順にする
            .reversed()
            // idだけ取り出す
            .map {
                it.id
            }
    }

    private val speedComparator: Comparator<SpeedInfo> = compareBy {
        it.speed
    }

    private data class SpeedInfo(
        val speed: Int,
        val id: Int,
    )
}
