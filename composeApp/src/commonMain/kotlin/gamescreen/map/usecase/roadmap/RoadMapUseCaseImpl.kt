package gamescreen.map.usecase.roadmap

import gamescreen.map.data.MapData
import gamescreen.map.domain.UIData
import gamescreen.map.domain.background.FrontObjectData
import gamescreen.map.usecase.resetnpc.ResetNPCPositionUseCase
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCase
import gamescreen.map.usecase.updatecellcontainplayer.UpdateCellContainPlayerUseCase

class RoadMapUseCaseImpl(
    private val setPlayerCenterUseCase: SetPlayerCenterUseCase,
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase,
    private val resetNPCPositionUseCase: ResetNPCPositionUseCase,
    private val updateCellContainPlayerUseCase: UpdateCellContainPlayerUseCase,
) : RoadMapUseCase {

    override suspend fun invoke(
        mapX: Int,
        mapY: Int,
        mapData: MapData,
    ): UIData {
        val backgroundData = resetBackgroundPositionUseCase.invoke(
            mapData = mapData,
            mapX = mapX,
            mapY = mapY,
        )
        resetNPCPositionUseCase.invoke(
            mapData = mapData,
            mapX = mapX,
            mapY = mapY,
        )
        val player = setPlayerCenterUseCase.invoke()
        updateCellContainPlayerUseCase.invoke(
            player = player,
            backgroundData = backgroundData,
        )

        val frontObjectData = FrontObjectData(
            backgroundData.fieldData.map {
                it.map { cell ->
                    val frontData = cell.collisionData.filter {
                        player.square.objectHeight < it.objectHeight
                    }

                    if (frontData.isEmpty()) {
                        null
                    } else {
                        cell.copy(
                            collisionData = frontData
                        )
                    }
                }
            }
        )

        return UIData(
            player = player,
            backgroundData = backgroundData,
            frontObjectData = frontObjectData,
        )
    }
}
