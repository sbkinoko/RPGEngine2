package gamescreen.map.repository.npc

import gamescreen.map.domain.collision.EventSquare
import kotlinx.coroutines.flow.StateFlow

interface NPCRepository {
    val npcStateFlow: StateFlow<EventSquare>

    fun setNpc(
        eventSquare: EventSquare,
    )
}
