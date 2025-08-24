package core.repository.memory.mapuistate

import gamescreen.map.domain.MapUiState
import kotlinx.coroutines.flow.StateFlow

interface MapUiStateRepository {
    val stateFlow: StateFlow<MapUiState>

    fun updateState(state: MapUiState)
}
