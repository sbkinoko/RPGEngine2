package core.domain

import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.background.ObjectData
import gamescreen.map.domain.npc.NPCData

val testMapUiState = MapUiState(
    player = Player(size = 0f),
    backgroundData = BackgroundData(emptyList()),
    frontObjectData = ObjectData(emptyList()),
    backObjectData = ObjectData(emptyList()),
    npcData = NPCData(emptyList()),
    playerIncludeCell = null,
)
