package gamescreen.map.repository.npc

import gamescreen.map.domain.npc.NPCData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NPCRepositoryImpl : NPCRepository {
    private val mutableNpcStateFlow: MutableStateFlow<NPCData> =
        MutableStateFlow(
            NPCData(
                listOf()
            )
        )

    override val npcStateFlow: StateFlow<NPCData>
        get() = mutableNpcStateFlow.asStateFlow()

    override fun setNpc(
        npcData: NPCData,
    ) {
        mutableNpcStateFlow.value = npcData
    }
}
