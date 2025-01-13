package gamescreen.map.usecase.collision.iscollided

import gamescreen.map.domain.collision.Square
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.npc.NPCRepository
import gamescreen.map.usecase.collision.list.GetCollisionListUseCase

// todo テスト作る
class IsCollidedUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
    private val getCollisionListUseCase: GetCollisionListUseCase,
    private val npcRepository: NPCRepository,
) : IsCollidedUseCase {
    override fun invoke(playerSquare: Square): Boolean {
        backgroundRepository
            .backgroundStateFlow
            .value
            .forEach { rowArray ->
                rowArray.forEach cell@{ cell ->
                    val collisionList = getCollisionListUseCase.invoke(
                        backgroundCell = cell,
                    )

                    if (collisionList.isEmpty()) {
                        return@cell
                    }

                    collisionList.forEach {
                        if (it.isOverlap(playerSquare)) {
                            return true
                        }
                    }
                }
            }

        npcRepository.npcStateFlow.value.forEach {
            if (it.isOverlap(playerSquare)
            ) {
                return true
            }
        }
        return false
    }
}
