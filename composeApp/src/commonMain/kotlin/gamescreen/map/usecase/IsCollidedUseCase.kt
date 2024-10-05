package gamescreen.map.usecase

import gamescreen.map.domain.collision.Square
import gamescreen.map.repository.backgroundcell.BackgroundRepository

class IsCollidedUseCase(
    private val repository: BackgroundRepository
) {

    /**
     * 障害物と衝突しているかどうかをチェック
     */
    operator fun invoke(square: Square): Boolean {
        repository.background.forEach { rowArray ->
            rowArray.forEach { cell ->
                if (cell.collisionList.isNotEmpty()) {
                    cell.collisionList.forEach {
                        if (it.isOverlap(square)) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }
}
