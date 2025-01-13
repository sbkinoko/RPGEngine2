package gamescreen.map.repository.npc

import gamescreen.map.domain.collision.square.EventSquare
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import values.EventType

class NPCRepositoryImpl : NPCRepository {
    private val mutableNpcStateFlow: MutableStateFlow<List<EventSquare>> =
        MutableStateFlow(
            listOf()
        )

    override val npcStateFlow: StateFlow<List<EventSquare>>
        get() = mutableNpcStateFlow.asStateFlow()

    override fun setNpc(
        eventSquares: List<EventSquare>,
    ) {
        mutableNpcStateFlow.value = eventSquares
    }

    companion object {
        val eventSquare = EventSquare(
            eventID = EventType.Talk,
            size = MapViewModel.VIRTUAL_SCREEN_SIZE / 5f,
            x = MapViewModel.VIRTUAL_SCREEN_SIZE * 3f / 5f,
            y = MapViewModel.VIRTUAL_SCREEN_SIZE * 3f / 5f,
        )
    }
}
