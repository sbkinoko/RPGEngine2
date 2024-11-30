package gamescreen.map.usecase.event.cellevent

import core.domain.mapcell.MapConst.Companion.TOWN_1I
import core.domain.mapcell.MapConst.Companion.TOWN_1O
import gamescreen.map.data.LoopMap
import gamescreen.map.data.NonLoopMap
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.usecase.roadmap.RoadMapUseCase

class CellEventUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
    private val roadMapDataUseCase: RoadMapUseCase,
) : CellEventUseCase {
    override fun invoke(cellId: Any) {
        when (cellId) {
            TOWN_1I -> {
                backgroundRepository.mapData = NonLoopMap()
                roadMapDataUseCase.invoke(
                    mapX = 0,
                    mapY = 2,
                    mapData = NonLoopMap(),
                )
            }

            TOWN_1O -> {
                roadMapDataUseCase.invoke(
                    mapX = 4,
                    mapY = 9,
                    mapData = LoopMap()
                )
            }
        }
    }
}
