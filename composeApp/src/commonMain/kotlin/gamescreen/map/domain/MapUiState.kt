package gamescreen.map.domain

import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.npc.NPCData

data class MapUiState(
    val player: Player,
    val npcData: NPCData,
    val backgroundData: BackgroundData,

    val playerIncludeCell: BackgroundCell?,
)
