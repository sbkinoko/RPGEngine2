package gamescreen.map.usecase.roadmap

import gamescreen.map.data.toMap
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.UIData
import gamescreen.map.service.makefrontdata.MakeFrontDateService
import gamescreen.map.usecase.movetootherheight.MoveToOtherHeightUseCase
import gamescreen.map.usecase.resetnpc.ResetNPCPositionUseCase
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCase
import gamescreen.map.usecase.updatecellcontainplayer.UpdateCellContainPlayerUseCase

class RoadMapUseCaseImpl(
    private val setPlayerCenterUseCase: SetPlayerCenterUseCase,
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase,
    private val resetNPCPositionUseCase: ResetNPCPositionUseCase,
    private val updateCellContainPlayerUseCase: UpdateCellContainPlayerUseCase,
    private val moveToOtherHeightUseCase: MoveToOtherHeightUseCase,

    private val makeFrontDateService: MakeFrontDateService,
) : RoadMapUseCase {

    override suspend fun invoke(
        mapX: Int,
        mapY: Int,
        mapId: Int,
        playerHeight: ObjectHeight,
    ): UIData {
        val mapData = mapId.toMap()

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
        var player = setPlayerCenterUseCase.invoke()
        updateCellContainPlayerUseCase.invoke(
            player = player,
            backgroundData = backgroundData,
        )

        moveToOtherHeightUseCase.invoke(
            targetHeight = playerHeight,
            backgroundData = backgroundData,
        ) {
            player = it
        }

        val frontObjectData = makeFrontDateService(
            backgroundData = backgroundData,
            player = player,
        )

        return UIData(
            player = player,
            backgroundData = backgroundData,
            frontObjectData = frontObjectData.first,
            backObjectData = frontObjectData.second,
        )
    }
}
