package gamescreen.map.domain.npc

import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.collision.square.EventSquare

data class NPC(
    val npcType: NPCType,
    val eventSquare: EventSquare,
    val mapPoint: MapPoint,
)
