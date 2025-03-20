package gamescreen.map.usecase.collision.iscollidedevent

import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.collisionevent.EventCollisionRepository
import values.event.EventType

// fixme repositoryに依存しないようにしたい
class IsCollidedEventUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
    private val eventCollisionRepository: EventCollisionRepository,
) : IsCollidedEventUseCase {

    override fun invoke(
        playerSquare: Rectangle,
    ): EventType? {
        backgroundRepository
            .backgroundStateFlow
            .value
            .fieldData
            .forEach { rowArray ->
                rowArray.forEach cell@{ cell ->
                    val collisionList = eventCollisionRepository.collisionData(
                        cellType = cell.cellType,
                        square = cell,
                    )

                    if (collisionList.isEmpty()) {
                        return@cell
                    }

                    val centerSquare = playerSquare.run {
                        NormalSquare(
                            size = 0f,
                            x = leftSide + width / 2,
                            y = topSide + height / 2,
                        )
                    }

                    collisionList.forEach {
                        if (centerSquare.isIn(it)) {
                            return it.eventType
                        }
                    }
                }
            }
        return null
    }
}
