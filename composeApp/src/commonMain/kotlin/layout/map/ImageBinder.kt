package layout.map

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import rpgengine.composeapp.generated.resources.Res
import rpgengine.composeapp.generated.resources.bg_00
import rpgengine.composeapp.generated.resources.bg_02
import rpgengine.composeapp.generated.resources.bg_null

class ImageBinder {

    @OptIn(ExperimentalResourceApi::class)
    fun bind(imgId: Int): DrawableResource {
        return when (imgId) {
            1 -> Res.drawable.bg_00
            2 -> Res.drawable.bg_02
            else -> Res.drawable.bg_null
        }
    }
}