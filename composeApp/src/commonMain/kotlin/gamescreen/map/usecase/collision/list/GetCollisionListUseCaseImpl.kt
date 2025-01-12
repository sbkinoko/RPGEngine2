package gamescreen.map.usecase.collision.list

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.collision.CollisionDetectShape
import gamescreen.map.repository.collision.CollisionRepository

class GetCollisionListUseCaseImpl(
    private val collisionRepository: CollisionRepository,
) : GetCollisionListUseCase {
    override fun invoke(backgroundCell: BackgroundCell): List<CollisionDetectShape> {
        return backgroundCell.run {
            collisionRepository
                .collisionData(
                    square = square,
                    cellType = cellType,
                )
        }
    }
}
