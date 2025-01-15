package gamescreen.map.repository.npc

import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.collision.square.EventSquare
import gamescreen.map.domain.npc.NPC
import gamescreen.map.domain.npc.NPCType
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import values.EventType

class NPCRepositoryImpl : NPCRepository {
    private val mutableNpcStateFlow: MutableStateFlow<List<NPC>> =
        MutableStateFlow(
            listOf()
        )

    override val npcStateFlow: StateFlow<List<NPC>>
        get() = mutableNpcStateFlow.asStateFlow()

    override fun setNpc(
        npcList: List<NPC>,
    ) {
        mutableNpcStateFlow.value = npcList
    }

    companion object {
        val npc = NPC(
            npcType = NPCType.GIRL,
            mapPoint = MapPoint(0, 0),
            eventSquare = EventSquare(
                eventID = EventType.Talk,
                size = MapViewModel.VIRTUAL_SCREEN_SIZE / 5f,
                x = MapViewModel.VIRTUAL_SCREEN_SIZE * 3f / 5f,
                y = MapViewModel.VIRTUAL_SCREEN_SIZE * 3f / 5f,
            ),
        )
    }
}
