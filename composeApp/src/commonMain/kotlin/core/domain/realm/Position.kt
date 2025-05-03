package core.domain.realm

import gamescreen.map.domain.ObjectHeight

data class Position(
    var mapX: Int = 0,
    var mapY: Int = 0,
    var playerX: Float = 0f,
    var playerY: Float = 0f,
    var objectHeight: ObjectHeight,
)
