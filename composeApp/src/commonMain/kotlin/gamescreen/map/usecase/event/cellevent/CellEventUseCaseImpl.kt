package gamescreen.map.usecase.event.cellevent

import core.domain.mapcell.CellType
import gamescreen.map.data.ID_LOOP
import gamescreen.map.data.ID_NON_LOOP
import gamescreen.map.data.NonLoopMap
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
import gamescreen.map.domain.UIData
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.usecase.roadmap.RoadMapUseCase

class CellEventUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
    private val roadMapDataUseCase: RoadMapUseCase,
) : CellEventUseCase {
    override suspend fun invoke(
        cellId: CellType,
        player: Player,
    ): UIData? {
        // セルがイベントでなければ何もしない
        if (cellId !is CellType.EventCell) {
            return null
        }

        return when (cellId) {
            CellType.Town1I -> {
                backgroundRepository.mapData = NonLoopMap()
                roadMapDataUseCase.invoke(
                    mapX = 0,
                    mapY = 2,
                    mapId = ID_NON_LOOP,
                    playerHeight = ObjectHeight.Ground(1),
                    player = player,
                )
            }

            CellType.Town1O -> {
                roadMapDataUseCase.invoke(
                    mapX = 4,
                    mapY = 9,
                    mapId = ID_LOOP,
                    playerHeight = ObjectHeight.Ground(1),
                    player = player,
                )
            }
        }
    }
}
