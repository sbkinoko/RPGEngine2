package core.usecase.restart

import core.usecase.heal.MaxHealUseCase
import data.INITIAL_MAP_DATA
import data.INITIAL_MAP_X
import data.INITIAL_MAP_Y
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
import gamescreen.map.domain.UIData
import gamescreen.map.usecase.roadmap.RoadMapUseCase

class RestartUseCaseImpl(
    private val maxHealUseCase: MaxHealUseCase,
    private val roadMapUseCase: RoadMapUseCase,
) : RestartUseCase {
    override suspend fun invoke(
        player: Player,
    ): UIData {
        maxHealUseCase.invoke()
        return roadMapUseCase.invoke(
            mapX = INITIAL_MAP_X,
            mapY = INITIAL_MAP_Y,
            mapId = INITIAL_MAP_DATA,
            playerHeight = ObjectHeight.Ground(1),
            player = player,
        )
    }
}
