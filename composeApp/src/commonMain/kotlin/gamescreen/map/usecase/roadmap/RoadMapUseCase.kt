package gamescreen.map.usecase.roadmap

import gamescreen.map.data.MapID
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player

// todo test作る
interface RoadMapUseCase {
    /**
     * 中心を指定して背景画像を再度読み込む
     */
    suspend operator fun invoke(
        mapX: Int,
        mapY: Int,
        mapId: MapID,
        playerHeight: ObjectHeight,
        player: Player,
    ): MapUiState
}
