package gamescreen.map.repository.npc

import gamescreen.map.domain.collision.square.EventSquare
import kotlinx.coroutines.flow.StateFlow

interface NPCRepository {
    val npcStateFlow: StateFlow<List<EventSquare>>

    fun setNpc(
        eventSquares: List<EventSquare>,
    )
}
