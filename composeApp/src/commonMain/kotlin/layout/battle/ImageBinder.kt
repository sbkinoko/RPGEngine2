package layout.battle

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import rpgengine.composeapp.generated.resources.Res
import rpgengine.composeapp.generated.resources.monster_001_9
import rpgengine.composeapp.generated.resources.monster_002_3
import rpgengine.composeapp.generated.resources.monster_003_1

class ImageBinder {

    companion object {
        /**
         * idと画像を紐づけ
         * @param imgId モンスターのID
         */
        @OptIn(ExperimentalResourceApi::class)
        fun bind(imgId: Int): DrawableResource {
            return when (imgId) {
                1 -> Res.drawable.monster_001_9
                2 -> Res.drawable.monster_002_3
                else -> Res.drawable.monster_003_1
            }
        }
    }
}
