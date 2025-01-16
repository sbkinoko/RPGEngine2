package gamescreen.map.layout.npc

import gamescreen.map.domain.npc.NPCType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import rpgengine.composeapp.generated.resources.Res
import rpgengine.composeapp.generated.resources.npc_1_1_1
import rpgengine.composeapp.generated.resources.npc_2

class ImageBinderNPC {

    /**
     * NPCの紐づけ
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bind(
        npcType: NPCType,
    ): DrawableResource {

        return when (npcType) {
            NPCType.GIRL -> Res.drawable.npc_1_1_1
            NPCType.MARCHANT -> Res.drawable.npc_2
        }
    }
}
