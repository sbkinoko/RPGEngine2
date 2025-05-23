package gamescreen.map.usecase.movetootherheight

import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight

interface MoveToOtherHeightUseCase {

    /**
     * @param targetHeight 移動したい高さ
     */
    suspend operator fun invoke(
        targetHeight: ObjectHeight,
        mapUiState: MapUiState,
        update: (MapUiState) -> Unit,
    )
}
