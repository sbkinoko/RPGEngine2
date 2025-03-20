package gamescreen.map.usecase.collision.list

import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.repository.collision.CollisionRepository

class GetCollisionListUseCaseImpl(
    private val collisionRepository: CollisionRepository,
) : GetCollisionListUseCase {
    override fun invoke(backgroundCell: BackgroundCell): List<ShapeCollisionDetect> {
        return backgroundCell.run {
            collisionRepository
                .collisionData(
                    rectangle = rectangle,
                    cellType = cellType,
                )
        }
    }
}
