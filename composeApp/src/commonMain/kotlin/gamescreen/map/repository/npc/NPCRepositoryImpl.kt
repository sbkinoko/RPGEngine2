package gamescreen.map.repository.npc

import gamescreen.map.domain.collision.EventSquare
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import values.EventType

class NPCRepositoryImpl : NPCRepository {
    private val mutableNpcStateFlow = MutableStateFlow<EventSquare>(
        EventSquare(
            eventID = EventType.Talk,
            size = MapViewModel.VIRTUAL_SCREEN_SIZE / 5f
        ).apply {
            moveTo(
                x = MapViewModel.VIRTUAL_SCREEN_SIZE * 3f / 5f,
                y = MapViewModel.VIRTUAL_SCREEN_SIZE * 3f / 5f,
            )
        }
    )

    override val npcStateFlow: StateFlow<EventSquare>
        get() = mutableNpcStateFlow.asStateFlow()
}
