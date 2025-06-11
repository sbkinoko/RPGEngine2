package gamescreen.map.usecase.collision.list

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.repository.collision.CollisionRepository

class GetCollisionListUseCaseImpl(
    private val collisionRepository: CollisionRepository,
) : GetCollisionListUseCase {
    override fun invoke(
        rectangle: Rectangle,
        cellType: CellType,
    ): List<ShapeCollisionDetect> {
        return collisionRepository
            .collisionData(
                rectangle = rectangle,
                cellType = cellType,
            )
    }
}
