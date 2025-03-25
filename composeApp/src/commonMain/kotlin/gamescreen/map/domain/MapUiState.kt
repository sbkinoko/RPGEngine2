package gamescreen.map.domain

import gamescreen.map.domain.npc.NPCData

data class MapUiState(
    val player: Player,
    val npcData: NPCData,
)
