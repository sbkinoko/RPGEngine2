package gamescreen.map.usecase.roadmap

import gamescreen.map.data.MapData

// todo test作る
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
