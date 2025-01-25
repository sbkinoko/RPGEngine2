package gamescreen.map.repository.npc

import gamescreen.map.domain.npc.NPC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
}
