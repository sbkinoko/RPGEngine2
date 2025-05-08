package gamescreen.battle.domain

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import rpgengine.composeapp.generated.resources.Res
import rpgengine.composeapp.generated.resources.bt_bg_01
import rpgengine.composeapp.generated.resources.bt_bg_02
import rpgengine.composeapp.generated.resources.bt_bg_sand
import rpgengine.composeapp.generated.resources.npc_3

enum class BattleBackgroundType {
    Glass,
    Road,
    Sand,
    Event,
}

@OptIn(ExperimentalResourceApi::class)
fun BattleBackgroundType.toResource(): DrawableResource {
    return when (this) {
        BattleBackgroundType.Glass -> Res.drawable.bt_bg_01
        BattleBackgroundType.Road -> Res.drawable.bt_bg_02
        BattleBackgroundType.Event -> Res.drawable.npc_3
        BattleBackgroundType.Sand -> Res.drawable.bt_bg_sand
    }
}
