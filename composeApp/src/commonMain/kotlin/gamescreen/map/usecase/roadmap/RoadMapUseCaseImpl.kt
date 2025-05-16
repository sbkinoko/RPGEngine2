package gamescreen.map.usecase.roadmap

import gamescreen.map.data.toMap
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
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
        player: Player,
    ): UIData {
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
        )

        updateCellContainPlayerUseCase.invoke(
            player = newPlayer,
            backgroundData = backgroundData,
        )

        moveToOtherHeightUseCase.invoke(
            targetHeight = playerHeight,
            backgroundData = backgroundData,
            player = newPlayer,
            npcData = npcData,
        ) {
//            newPlayer = it
        }

        val frontObjectData = makeFrontDateService(
            backgroundData = backgroundData,
            player = newPlayer,
        )

        return UIData(
            player = newPlayer,
            backgroundData = backgroundData,
            frontObjectData = frontObjectData.first,
            backObjectData = frontObjectData.second,
            npcData = npcData,
        )
    }
}
