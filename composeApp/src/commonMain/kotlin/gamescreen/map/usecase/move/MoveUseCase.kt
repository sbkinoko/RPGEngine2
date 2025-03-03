package gamescreen.map.usecase.move

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.viewmodel.MapViewModel.Companion.MOVE_BORDER
import gamescreen.map.viewmodel.MapViewModel.Companion.VIRTUAL_SCREEN_SIZE

interface MoveUseCase {
    companion object {
        private val pFieldSquare: NormalSquare
            get() = NormalSquare(
                x = 0f,
                y = 0f,
                size = VIRTUAL_SCREEN_SIZE.toFloat(),
            )

        private val pPlayerMoveArea
            get() = NormalSquare.smallSquare(
                size = VIRTUAL_SCREEN_SIZE,
                rate = MOVE_BORDER,
            )
    }

    /**
     * 移動
     */
    suspend operator fun invoke(
        actualVelocity: Velocity,
        tentativeVelocity: Velocity,
        fieldSquare: NormalSquare = pFieldSquare,
        playerMoveArea: NormalSquare = pPlayerMoveArea,
    )
}
