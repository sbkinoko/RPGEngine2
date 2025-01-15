package gamescreen.map.usecase.roadmap

import gamescreen.map.data.MapData
import gamescreen.map.usecase.UpdateCellContainPlayerUseCase
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCase

class RoadMapUseCaseImpl(
    private val setPlayerCenterUseCase: SetPlayerCenterUseCase,
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase,
    private val updateCellContainPlayerUseCase: UpdateCellContainPlayerUseCase,
) : RoadMapUseCase {
    override fun invoke(
        mapX: Int,
        mapY: Int,
        mapData: MapData,
    ) {
        resetBackgroundPositionUseCase.invoke(
            mapData = mapData,
            mapX = mapX,
            mapY = mapY,
        )
        setPlayerCenterUseCase.invoke()
        updateCellContainPlayerUseCase.invoke()
    }
}
