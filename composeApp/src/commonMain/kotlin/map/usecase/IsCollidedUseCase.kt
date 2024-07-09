package map.usecase

import map.domain.collision.Square
import map.repository.backgroundcell.BackgroundRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IsCollidedUseCase : KoinComponent {
    private val repository: BackgroundRepository by inject()

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
