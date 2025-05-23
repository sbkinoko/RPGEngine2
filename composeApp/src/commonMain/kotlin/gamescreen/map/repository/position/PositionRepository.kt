package gamescreen.map.repository.position

import core.domain.realm.Position
import gamescreen.map.data.MapData
import gamescreen.map.domain.ObjectHeight

interface PositionRepository {
    fun save(
        x: Int,
        y: Int,
        playerDx: Float,
        playerDy: Float,
        objectHeight: ObjectHeight,
        mapData: MapData,
    )

    fun position(): Position
}
