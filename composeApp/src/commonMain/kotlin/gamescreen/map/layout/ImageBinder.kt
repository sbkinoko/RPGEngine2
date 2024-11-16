package gamescreen.map.layout

import gamescreen.map.domain.ConnectType
import gamescreen.map.usecase.decideconnectcype.DecideConnectTypeUseCase
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import rpgengine.composeapp.generated.resources.Res
import rpgengine.composeapp.generated.resources.bg_00
import rpgengine.composeapp.generated.resources.bg_02
import rpgengine.composeapp.generated.resources.bg_20
import rpgengine.composeapp.generated.resources.bg_null
import rpgengine.composeapp.generated.resources.ob_01_01
import rpgengine.composeapp.generated.resources.ob_01_02
import rpgengine.composeapp.generated.resources.ob_01_03
import rpgengine.composeapp.generated.resources.ob_01_05
import rpgengine.composeapp.generated.resources.ob_01_06
import rpgengine.composeapp.generated.resources.ob_01_07
import rpgengine.composeapp.generated.resources.ob_01_08
import rpgengine.composeapp.generated.resources.ob_01_09
import rpgengine.composeapp.generated.resources.ob_01_10
import rpgengine.composeapp.generated.resources.ob_01_11
import rpgengine.composeapp.generated.resources.ob_01_12
import rpgengine.composeapp.generated.resources.ob_98_1

class ImageBinder : KoinComponent {
    private val decideConnectTypeUseCase: DecideConnectTypeUseCase by inject()

    /**
     * idと画像を紐づけ
     * @param aroundCellId 周囲のマスの背景画像のid
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bindBackGround(
        aroundCellId: Array<Array<Int>>,
    ): DrawableResource {
        val imgId = aroundCellId[1][1]

        return when (imgId) {
            1 -> Res.drawable.bg_00
            2 -> Res.drawable.bg_02
            3, 4 -> Res.drawable.bg_20
            5 -> {
                when (decideConnectTypeUseCase(aroundCellId)) {
                    ConnectType.Vertical -> Res.drawable.ob_01_01
                    ConnectType.Horizontal -> Res.drawable.ob_01_02
                    ConnectType.Cross -> Res.drawable.ob_01_03
                    ConnectType.RightToUp -> Res.drawable.ob_01_05
                    ConnectType.LeftTopUp -> Res.drawable.ob_01_06
                    ConnectType.LeftTopDown -> Res.drawable.ob_01_07
                    ConnectType.RightToDown -> Res.drawable.ob_01_08
                    ConnectType.WithoutLeft -> Res.drawable.ob_01_09
                    ConnectType.WithoutDown -> Res.drawable.ob_01_10
                    ConnectType.WithoutRight -> Res.drawable.ob_01_11
                    ConnectType.WithoutUp -> Res.drawable.ob_01_12
                }
            }

            6 -> Res.drawable.bg_00

            else -> Res.drawable.bg_null
        }
    }


    /**
     * idと画像を紐づけ
     * @param aroundCellId 周囲のマスの背景画像のid
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bindObject(
        aroundCellId: Array<Array<Int>>,
    ): DrawableResource? {
        val imgId = aroundCellId[1][1]

        return when (imgId) {
            6 -> Res.drawable.ob_98_1

            else -> null
        }
    }
}
