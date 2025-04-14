package gamescreen.map.domain.npc

import androidx.compose.runtime.Stable

@Stable
data class NPCData(
    val npcList: List<NPC>,
) {
    fun <T> map(callback: (NPC) -> T): List<T> {
        return npcList.map {
            callback.invoke(it)
        }
    }

    fun map(callback: (NPC) -> NPC): NPCData {
        return NPCData(
            npcList.map {
                callback.invoke(it)
            }
        )
    }

    inline fun forEach(callback: (NPC) -> Unit) {
        npcList.forEach(callback)
    }
}
