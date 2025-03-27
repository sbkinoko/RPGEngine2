package gamescreen.map.usecase.roadmap

import gamescreen.map.data.MapData
import gamescreen.map.domain.Player
import gamescreen.map.domain.UIData
import gamescreen.map.domain.npc.NPCData
import gamescreen.map.usecase.UpdateCellContainPlayerUseCase
import gamescreen.map.usecase.resetnpc.ResetNPCPositionUseCase
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import gamescreen.map.usecase.setplayercenter.SetPlayerCenterUseCase

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
        setPlayerCenterUseCase.invoke()
        updateCellContainPlayerUseCase.invoke()

        return UIData(
            backgroundData = backgroundData,
            player = Player(
                size = 0f,
            ),
            npcData = NPCData(emptyList()),
        )
    }
}
