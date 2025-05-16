package gamescreen.map.usecase.roadmap

import gamescreen.map.data.toMap
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
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
        mapId: Int,
        playerHeight: ObjectHeight,
        player: Player,
    ): MapUiState {
        val mapData = mapId.toMap()

        val backgroundData = resetBackgroundPositionUseCase.invoke(
            mapData = mapData,
            mapX = mapX,
            mapY = mapY,
        )

        val npcData = resetNPCPositionUseCase.invoke(
            mapData = mapData,
            mapX = mapX,
            mapY = mapY,
        )

        val newPlayer = setPlayerCenterUseCase.invoke(
            player = player,
        ).changeHeight(
            targetHeight = playerHeight,
        )

        val cell = updateCellContainPlayerUseCase.invoke(
            player = newPlayer,
            backgroundData = backgroundData,
        )

        val frontObjectData = makeFrontDateService(
            backgroundData = backgroundData,
            player = newPlayer,
        )

        return MapUiState(
            player = newPlayer,
            backgroundData = backgroundData,
            frontObjectData = frontObjectData.first,
            backObjectData = frontObjectData.second,
            npcData = npcData,
            playerIncludeCell = cell,
        )
    }
}
