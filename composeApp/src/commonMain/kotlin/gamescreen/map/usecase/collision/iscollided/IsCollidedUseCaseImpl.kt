package gamescreen.map.usecase.collision.iscollided

import gamescreen.map.domain.collision.Square
import gamescreen.map.repository.backgroundcell.BackgroundRepository

class IsCollidedUseCaseImpl(
    private val backgroundRepository: BackgroundRepository
) : IsCollidedUseCase {
    override fun invoke(playerSquare: Square): Boolean {

        backgroundRepository.backgroundStateFlow.value.forEach { rowArray ->
            rowArray.forEach { cell ->
                if (cell.collisionList.isNotEmpty()) {
                    cell.collisionList.forEach {
                        if (it.isOverlap(playerSquare)) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }
}
