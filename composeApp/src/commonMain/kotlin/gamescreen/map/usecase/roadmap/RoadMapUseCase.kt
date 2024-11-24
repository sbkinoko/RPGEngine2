package gamescreen.map.usecase.roadmap

import gamescreen.map.domain.MapData

interface RoadMapUseCase {
    /**
     * 中心を指定して背景画像を再度読み込む
     */
    operator fun invoke(
        mapX: Int,
        mapY: Int,
        mapData: MapData,
    )
}
