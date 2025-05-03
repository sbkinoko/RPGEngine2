package gamescreen.map.repository.position

import core.domain.realm.Position

interface PositionRepository {
    fun save(
        x: Int,
        y: Int,
        playerDx: Float,
        playerDy: Float,
    )

    fun position(): Position
}
