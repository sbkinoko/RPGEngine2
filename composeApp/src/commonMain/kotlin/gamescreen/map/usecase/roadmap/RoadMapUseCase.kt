package gamescreen.map.usecase.roadmap

import gamescreen.map.data.MapData
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.UIData

// todo test作る
interface RoadMapUseCase {
    /**
     * 中心を指定して背景画像を再度読み込む
     */
    suspend operator fun invoke(
        mapX: Int,
        mapY: Int,
        mapData: MapData,
        playerHeight: ObjectHeight,
    ): UIData
}
