package gamescreen.map.repository.position

import core.domain.realm.Position

interface PositionRepository {
    fun save(
        x: Int,
        y: Int,
    )

    fun position(): Position
}
