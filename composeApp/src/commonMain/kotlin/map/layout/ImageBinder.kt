package map.layout

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import rpgengine.composeapp.generated.resources.Res
import rpgengine.composeapp.generated.resources.bg_00
import rpgengine.composeapp.generated.resources.bg_02
import rpgengine.composeapp.generated.resources.bg_20
import rpgengine.composeapp.generated.resources.bg_null
import rpgengine.composeapp.generated.resources.ob_01_03

class ImageBinder {

    /**
     * idと画像を紐づけ
     * @param imgId 背景画像のid
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bind(imgId: Int): DrawableResource {
        return when (imgId) {
            1 -> Res.drawable.bg_00
            2 -> Res.drawable.bg_02
            3, 4 -> Res.drawable.bg_20
            5 -> Res.drawable.ob_01_03
            else -> Res.drawable.bg_null
        }
    }
}
