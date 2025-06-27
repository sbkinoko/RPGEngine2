package gamescreen.map.layout.npc

import gamescreen.map.domain.npc.NPCType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import rpgengine.composeapp.generated.resources.Res
import rpgengine.composeapp.generated.resources.npc_
import rpgengine.composeapp.generated.resources.npc_1
import rpgengine.composeapp.generated.resources.npc_1_1_1
import rpgengine.composeapp.generated.resources.npc_2
import rpgengine.composeapp.generated.resources.npc_3

class ImageBinder {

    /**
     * NPCの紐づけ
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bind(
        npcType: NPCType,
    ): DrawableResource {
        return when (npcType) {
            NPCType.GIRL -> Res.drawable.npc_1_1_1
            NPCType.MERCHANT -> Res.drawable.npc_2
            NPCType.ENEMY -> Res.drawable.npc_3
            NPCType.BOY -> Res.drawable.npc_1
            NPCType.TEMPLATE -> Res.drawable.npc_
        }
    }
}
