package gamescreen.map.usecase.roadmap

import gamescreen.map.data.MapData
import gamescreen.map.domain.UIData
import gamescreen.map.service.makefrontdata.MakeFrontDateService
import gamescreen.map.usecase.resetnpc.ResetNPCPositionUseCase
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCase
import gamescreen.map.usecase.updatecellcontainplayer.UpdateCellContainPlayerUseCase

class RoadMapUseCaseImpl(
    private val setPlayerCenterUseCase: SetPlayerCenterUseCase,
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase,
    private val resetNPCPositionUseCase: ResetNPCPositionUseCase,
    private val updateCellContainPlayerUseCase: UpdateCellContainPlayerUseCase,

    private val makeFrontDateService: MakeFrontDateService,
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

        val frontObjectData = makeFrontDateService(
            backgroundData = backgroundData,
            player = player,
        )

        return UIData(
            player = player,
            backgroundData = backgroundData,
            frontObjectData = frontObjectData,
        )
    }
}
