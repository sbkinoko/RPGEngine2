package gamescreen.map.usecase.collision.iscollided

import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.repository.npc.NPCRepository

// fixme repositoryに依存しないようにしたい
class IsCollidedUseCaseImpl(
    private val npcRepository: NPCRepository,
) : IsCollidedUseCase {

    override fun invoke(
        playerSquare: Rectangle,
        backgroundData: BackgroundData,
    ): Boolean {
        backgroundData
            .fieldData
            .forEach { rowArray ->
                rowArray.forEach cell@{ cell ->
                    val collisionList =
                        cell.collisionData

                    if (collisionList.isEmpty()) {
                        return@cell
                    }

                    collisionList.forEach {
                        if (it.isOverlap(
                                playerSquare,
                                cell.baseX,
                                cell.baseY,
                            )
                        ) {
                            return true
                        }
                    }
                }
            }

        npcRepository.npcStateFlow.value.forEach { npc ->
            npc.eventRectangle.let {
                if (it.isOverlap(playerSquare)
                ) {
                    return true
                }
            }
        }
        return false
    }
}
