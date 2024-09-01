package map.layout

import map.usecase.decideconnectcype.DecideConnectTypeUseCase
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import rpgengine.composeapp.generated.resources.Res
import rpgengine.composeapp.generated.resources.bg_00
import rpgengine.composeapp.generated.resources.bg_02
import rpgengine.composeapp.generated.resources.bg_20
import rpgengine.composeapp.generated.resources.bg_null
import rpgengine.composeapp.generated.resources.ob_01_03

class ImageBinder : KoinComponent {
    private val decideConnectTypeUseCase: DecideConnectTypeUseCase by inject()

    /**
     * idと画像を紐づけ
     * @param aroundCellId 周囲のマスの背景画像のid
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bind(
        aroundCellId: Array<Array<Int>>,
    ): DrawableResource {
        val imgId = aroundCellId[1][1]

        return when (imgId) {
            1 -> Res.drawable.bg_00
            2 -> Res.drawable.bg_02
            3, 4 -> Res.drawable.bg_20
            5 -> {
                Res.drawable.ob_01_03
            }
            else -> Res.drawable.bg_null
        }
    }
}
