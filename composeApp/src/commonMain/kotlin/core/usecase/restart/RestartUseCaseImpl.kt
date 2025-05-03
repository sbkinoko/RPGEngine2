package core.usecase.restart

import core.usecase.heal.MaxHealUseCase
import data.INITIAL_MAP_DATA
import data.INITIAL_MAP_X
import data.INITIAL_MAP_Y
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.usecase.roadmap.RoadMapUseCase

class RestartUseCaseImpl(
    private val maxHealUseCase: MaxHealUseCase,
    private val roadMapUseCase: RoadMapUseCase,
) : RestartUseCase {
    override suspend fun invoke() {
        maxHealUseCase.invoke()
        roadMapUseCase.invoke(
            mapX = INITIAL_MAP_X,
            mapY = INITIAL_MAP_Y,
            mapData = INITIAL_MAP_DATA,
            playerHeight = ObjectHeight.Ground(1),
        )
    }
}
