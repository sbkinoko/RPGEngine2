package gamescreen.map.repository.npc

import gamescreen.map.domain.npc.NPCData
import kotlinx.coroutines.flow.StateFlow

interface NPCRepository {
    val npcStateFlow: StateFlow<NPCData>

    fun setNpc(
        npcData: NPCData,
    )
}
