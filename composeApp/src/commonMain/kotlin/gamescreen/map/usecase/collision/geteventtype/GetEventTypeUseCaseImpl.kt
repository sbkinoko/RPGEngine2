package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.collision.EventObject
import gamescreen.map.domain.collision.Square
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import values.EventConstants

class GetEventTypeUseCaseImpl(
    private val backgroundRepository: BackgroundRepository
) : GetEventTypeUseCase {
    override fun invoke(
        square: Square,
    ): EventConstants {
        backgroundRepository.background.forEach { rowArray ->
            rowArray.forEach { cell ->
                if (cell.collisionList.isNotEmpty()) {
                    cell.collisionList.forEach {
                        if (it.isOverlap(square)) {
                            if (it is EventObject) {
                                return it.eventID
                            }
                        }
                    }
                }
            }
        }
        return EventConstants.None
    }

}
