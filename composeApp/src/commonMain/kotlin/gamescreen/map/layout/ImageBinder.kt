package gamescreen.map.layout

import core.domain.mapcell.MapConst.Companion.BOX____
import core.domain.mapcell.MapConst.Companion.GLASS__
import core.domain.mapcell.MapConst.Companion.ROAD___
import core.domain.mapcell.MapConst.Companion.TOWN_1I
import core.domain.mapcell.MapConst.Companion.TOWN_1O
import core.domain.mapcell.MapConst.Companion.WATER__
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
        aroundCellId: Array<Array<Any>>,
    ): DrawableResource {
        val imgId = aroundCellId[1][1]

        return when (imgId) {
            GLASS__ -> Res.drawable.bg_00
            WATER__ -> Res.drawable.bg_02
            TOWN_1I, TOWN_1O -> Res.drawable.bg_20
            ROAD___ -> {
                when (decideConnectTypeUseCase.invoke(aroundCellId)) {
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

            BOX____ -> Res.drawable.bg_00

            else -> Res.drawable.bg_null
        }
    }


    /**
     * idと画像を紐づけ
     * @param aroundCellId 周囲のマスの背景画像のid
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bindObject(
        aroundCellId: Array<Array<Any>>,
    ): DrawableResource? {
        val imgId = aroundCellId[1][1]

        return when (imgId) {
            BOX____ -> Res.drawable.ob_98_1

            else -> null
        }
    }
}
