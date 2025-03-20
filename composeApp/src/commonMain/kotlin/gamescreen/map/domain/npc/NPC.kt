package gamescreen.map.domain.npc

import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.EventSquare
import values.event.EventType

data class NPC(
    val npcType: NPCType,
    val eventSquare: EventSquare,
    val mapPoint: MapPoint,
) {
    constructor(
        npcType: NPCType,
        mapPoint: MapPoint,
        eventType: EventType,
        size: Float,
    ) : this(
        npcType = npcType,
        mapPoint = mapPoint,
        eventSquare = EventSquare(
            eventType = eventType,
            size = size,
            objectHeight = ObjectHeight.Ground(1),
        ),
    )

    fun move(velocity: Velocity): NPC {
        return copy(
            eventSquare = eventSquare.move(
                dx = velocity.x,
                dy = velocity.y,
            )
        )
    }
}
