package gamescreen.map.usecase.event.cellevent

import core.domain.mapcell.CellType
import gamescreen.map.data.LoopMap
import gamescreen.map.data.NonLoopMap
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.usecase.roadmap.RoadMapUseCase

class CellEventUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
    private val roadMapDataUseCase: RoadMapUseCase,
) : CellEventUseCase {
    override suspend fun invoke(
        cellId: CellType,
    ) {
        // セルがイベントでなければ何もしない
        if (cellId !is CellType.EventCell) {
            return
        }

        when (cellId) {
            CellType.Town1I -> {
                backgroundRepository.mapData = NonLoopMap()
                roadMapDataUseCase.invoke(
                    mapX = 0,
                    mapY = 2,
                    mapData = NonLoopMap(),
                    playerHeight = ObjectHeight.Ground(1),
                )
            }

            CellType.Town1O -> {
                roadMapDataUseCase.invoke(
                    mapX = 4,
                    mapY = 9,
                    mapData = LoopMap(),
                    playerHeight = ObjectHeight.Ground(1),
                )
            }
        }
    }
}
