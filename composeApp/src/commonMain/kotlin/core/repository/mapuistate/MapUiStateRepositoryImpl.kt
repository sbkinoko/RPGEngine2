package core.repository.mapuistate

import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.background.ObjectData
import gamescreen.map.domain.npc.NPCData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapUiStateRepositoryImpl : MapUiStateRepository {
    private val mutableStateFlow = MutableStateFlow(
        MapUiState(
            player = Player(size = 0f),
            npcData = NPCData(emptyList()),
            backgroundData = BackgroundData(emptyList()),
            frontObjectData = ObjectData(emptyList()),
            backObjectData = ObjectData(emptyList()),
            playerIncludeCell = null,
        )
    )

    override val stateFlow: StateFlow<MapUiState>
        get() = mutableStateFlow.asStateFlow()

    override fun updateState(state: MapUiState) {
        mutableStateFlow.value = state
    }
}
