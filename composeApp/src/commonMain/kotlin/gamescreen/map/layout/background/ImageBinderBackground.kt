package gamescreen.map.layout.background

import common.extension.MapCounter
import core.domain.mapcell.CellType
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
import rpgengine.composeapp.generated.resources.bg_8
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
import rpgengine.composeapp.generated.resources.ob_98_0
import rpgengine.composeapp.generated.resources.ob_98_1
import rpgengine.composeapp.generated.resources.ob_bridge_center_bottom
import rpgengine.composeapp.generated.resources.ob_bridge_center_top
import rpgengine.composeapp.generated.resources.ob_bridge_right_bottom
import rpgengine.composeapp.generated.resources.ob_bridge_right_top
import rpgengine.composeapp.generated.resources.橋_上
import rpgengine.composeapp.generated.resources.橋_下

class ImageBinderBackground : KoinComponent {
    private val decideConnectTypeUseCase: DecideConnectTypeUseCase by inject()

    /**
     * idと画像を紐づけ
     * @param aroundCellId 周囲のマスの背景画像のid
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bindBackGround(
        aroundCellId: List<List<CellType>>,
    ): DrawableResource {
        val imgId = aroundCellId[1][1]

        return bindBackGround(
            imgId,
            aroundCellId,
        )
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun bindBackGround(
        cellType: CellType,
        aroundCellId: List<List<CellType>>,
    ): DrawableResource {
        return when (cellType) {
            CellType.Glass,
            CellType.BridgeLeftTop,
            CellType.BridgeLeftUnder,
            CellType.BridgeRightTop,
            CellType.BridgeRightUnder,
            CellType.BridgeCenterTop,
            CellType.BridgeCenterBottom,
                -> Res.drawable.bg_00

            CellType.Sand,
                -> Res.drawable.bg_8

            CellType.Water -> Res.drawable.bg_02
            CellType.Town1I, CellType.Town1O -> Res.drawable.bg_20
            CellType.Road -> {
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

            is CellType.Box -> return getMajorityBackGround(
                aroundCellId = aroundCellId,
            )

            CellType.Null,
            is CellType.TextCell,
                -> Res.drawable.bg_null
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun getMajorityBackGround(
        aroundCellId: List<List<CellType>>,
    ): DrawableResource {
        val mapCounter = MapCounter<CellType>()
        aroundCellId.map { list ->
            list.map {
                mapCounter.inc(it)
            }
        }

        val countList = mapCounter.ranking

        return bindBackGround(
            cellType = countList[0],
            aroundCellId = aroundCellId,
        )
    }

    /**
     * idと画像を紐づけ
     * @param cellType 周囲のマスとして利用する背景画像のid
     */
    @OptIn(ExperimentalResourceApi::class)
    fun bindObject(
        cellType: CellType,
    ): DrawableResource? {

        if (cellType !is CellType.ObjectCell) {
            return null
        }

        return when (cellType) {
            is CellType.Box -> {
                if (cellType.hasItem) {
                    Res.drawable.ob_98_1
                } else {
                    Res.drawable.ob_98_0
                }
            }

            CellType.BridgeLeftUnder -> Res.drawable.橋_下
            CellType.BridgeLeftTop -> Res.drawable.橋_上
            CellType.BridgeRightUnder -> Res.drawable.ob_bridge_right_bottom
            CellType.BridgeRightTop -> Res.drawable.ob_bridge_right_top
            CellType.BridgeCenterBottom -> Res.drawable.ob_bridge_center_bottom
            CellType.BridgeCenterTop -> Res.drawable.ob_bridge_center_top
        }
    }
}
