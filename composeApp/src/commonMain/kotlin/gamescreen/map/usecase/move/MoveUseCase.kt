package gamescreen.map.usecase.move

import gamescreen.map.domain.UIData
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.viewmodel.MapViewModel.Companion.MOVE_BORDER
import gamescreen.map.viewmodel.MapViewModel.Companion.VIRTUAL_SCREEN_SIZE

interface MoveUseCase {
    companion object {
        private val pFieldSquare: NormalRectangle
            get() = NormalRectangle(
                x = 0f,
                y = 0f,
                size = VIRTUAL_SCREEN_SIZE.toFloat(),
            )

        private val pPlayerMoveArea
            get() = NormalRectangle.smallSquare(
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
        backgroundData: BackgroundData,
        fieldSquare: NormalRectangle = pFieldSquare,
        playerMoveArea: NormalRectangle = pPlayerMoveArea,
    ): UIData
}
