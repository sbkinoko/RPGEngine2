package gamescreen.map.usecase.collision.iscollidedevent

import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.repository.collisionevent.EventCollisionRepository
import values.event.EventType

// fixme repositoryに依存しないようにしたい
class IsCollidedEventUseCaseImpl(
    private val eventCollisionRepository: EventCollisionRepository,
) : IsCollidedEventUseCase {

    override fun invoke(
        playerSquare: Rectangle,
        backgroundData: BackgroundData,
    ): EventType? {
        backgroundData
            .fieldData
            .forEach { rowArray ->
                rowArray.forEach cell@{ cell ->
                    // fixme  backgroundの情報を使う
                    val collisionList = eventCollisionRepository.collisionData(
                        cellType = cell.cellType,
                        rectangle = cell,
                    )

                    if (collisionList.isEmpty()) {
                        return@cell
                    }

                    val centerSquare = playerSquare.run {
                        NormalRectangle(
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
