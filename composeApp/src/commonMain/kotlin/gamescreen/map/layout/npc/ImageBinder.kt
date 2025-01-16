package gamescreen.map.layout.npc

import gamescreen.map.domain.npc.NPCType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import rpgengine.composeapp.generated.resources.Res
import rpgengine.composeapp.generated.resources.npc_1_1_1

class ImageBinderNPC {

    /**
     * NPCの紐づけ
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bind(
        npcType: NPCType,
    ): DrawableResource {
        return Res.drawable.npc_1_1_1
    }

}
