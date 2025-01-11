package gamescreen.map.usecase.collision.list

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.collision.CollisionDetectShape

interface GetCollisionListUseCase {

    operator fun invoke(
        backgroundCell: BackgroundCell,
    ): List<CollisionDetectShape>
}
