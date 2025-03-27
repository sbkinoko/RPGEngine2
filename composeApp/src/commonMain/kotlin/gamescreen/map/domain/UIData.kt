package gamescreen.map.domain

import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.npc.NPCData

data class UIData(
    val player: Player,
    val backgroundData: BackgroundData,
    val npcData: NPCData,
)
