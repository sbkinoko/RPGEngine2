package gamescreen.map.repository.npc

import gamescreen.map.domain.npc.NPC
import kotlinx.coroutines.flow.StateFlow

interface NPCRepository {
    val npcStateFlow: StateFlow<List<NPC>>

    fun setNpc(
        npcList: List<NPC>,
    )
}
