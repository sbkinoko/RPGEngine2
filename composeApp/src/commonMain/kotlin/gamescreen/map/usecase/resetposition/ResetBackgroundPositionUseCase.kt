package gamescreen.map.usecase.resetposition

import gamescreen.map.data.MapData

interface ResetBackgroundPositionUseCase {

    /**
     * マップの中心を指定して、マップデータをロードする
     */
    operator fun invoke(
        mapData: MapData,
        mapX: Int,
        mapY: Int,
    )
}
