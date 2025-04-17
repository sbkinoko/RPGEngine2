package gamescreen.map.domain

import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.background.FrontObjectData
import gamescreen.map.domain.npc.NPCData

data class UIData(
    val player: Player? = null,
    val backgroundData: BackgroundData? = null,
    val frontObjectData: FrontObjectData? = null,
    val npcData: NPCData? = null,
)
