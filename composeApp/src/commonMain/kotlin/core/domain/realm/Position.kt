package core.domain.realm

import data.INITIAL_MAP_DATA
import data.INITIAL_MAP_X
import data.INITIAL_MAP_Y
import gamescreen.map.data.MapID
import gamescreen.map.domain.ObjectHeight

data class Position(
    var mapX: Int = INITIAL_MAP_X,
    var mapY: Int = INITIAL_MAP_Y,
    var playerX: Float = 0f,
    var playerY: Float = 0f,
    var objectHeight: ObjectHeight,
    var mapId: MapID = INITIAL_MAP_DATA,
)
