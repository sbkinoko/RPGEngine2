package gamescreen.map.usecase.collision.list

import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.collision.ShapeCollisionDetect

interface GetCollisionListUseCase {

    operator fun invoke(
        backgroundCell: BackgroundCell,
    ): List<ShapeCollisionDetect>
}
