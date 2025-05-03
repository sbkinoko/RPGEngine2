package gamescreen.map.usecase.movetootherheight

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player

interface MoveToOtherHeightUseCase {

    /**
     * @param targetHeight 移動したい高さ
     */
    suspend operator fun invoke(
        targetHeight: ObjectHeight,
        update: (Player) -> Unit = {},
    )
}
