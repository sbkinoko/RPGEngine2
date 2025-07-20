package core.usecase.restart

import core.usecase.heal.MaxHealUseCase
import data.INITIAL_MAP_DATA
import data.INITIAL_MAP_X
import data.INITIAL_MAP_Y
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.ObjectHeightDetail
import gamescreen.map.usecase.roadmap.RoadMapUseCase

// fixme 再スタート先を場合によって変えられるようにする
class RestartUseCaseImpl(
    private val maxHealUseCase: MaxHealUseCase,
    private val roadMapUseCase: RoadMapUseCase,
) : RestartUseCase {
    override suspend fun invoke(
        mapUiState: MapUiState,
    ): MapUiState {
        maxHealUseCase.invoke()
        return roadMapUseCase.invoke(
            mapX = INITIAL_MAP_X,
            mapY = INITIAL_MAP_Y,
            mapId = INITIAL_MAP_DATA,
            playerHeight = ObjectHeight.Ground(
                ObjectHeightDetail.Normal
            ),
            player = mapUiState.player,
        )
    }
}
