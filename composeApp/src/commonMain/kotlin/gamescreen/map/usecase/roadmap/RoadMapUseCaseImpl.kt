package gamescreen.map.usecase.roadmap

import gamescreen.map.domain.MapData
import gamescreen.map.usecase.UpdateCellContainPlayerUseCase
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCase

class RoadMapUseCaseImpl(
    private val setPlayerCenterUseCase: SetPlayerCenterUseCase,
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase,
    private val updateCellContainPlayerUseCase: UpdateCellContainPlayerUseCase,
) : RoadMapUseCase {
    override fun invoke(mapX: Int, mapY: Int, mapData: MapData) {
        setPlayerCenterUseCase.invoke()
        resetBackgroundPositionUseCase(
            mapData = mapData,
            mapX = mapX,
            mapY = mapY,
        )
        updateCellContainPlayerUseCase()
    }
}
