package gamescreen.map.usecase.collision.list

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.repository.collision.CollisionRepository

class GetCollisionListUseCaseImpl(
    private val collisionRepository: CollisionRepository,
) : GetCollisionListUseCase {
    override fun invoke(backgroundCell: BackgroundCell): List<ShapeCollisionDetect> {
        return backgroundCell.run {
            collisionRepository
                .collisionData(
                    square = square,
                    cellType = cellType,
                )
        }
    }
}
