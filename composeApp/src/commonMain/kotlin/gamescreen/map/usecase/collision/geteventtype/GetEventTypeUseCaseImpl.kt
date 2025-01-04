package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.collision.EventObject
import gamescreen.map.domain.collision.Square
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.npc.NPCRepository
import values.EventType

class GetEventTypeUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
    private val npcRepository: NPCRepository,
) : GetEventTypeUseCase {
    override fun invoke(
        square: Square,
    ): EventType {
        backgroundRepository.backgroundStateFlow.value.forEach { rowArray ->
            for (cell in rowArray) {
                // 物がないので次を探索
                if (cell.collisionList.isEmpty()) {
                    continue
                }

                for (shape in cell.collisionList) {
                    // 重なってないので次へ
                    if (shape.isOverlap(square).not()) {
                        continue
                    }

                    // イベントオブジェではないので次へ
                    if (shape !is EventObject) {
                        continue
                    }

                    //　イベントオブジェなのでIDを返す
                    return shape.eventID
                }
            }
        }

        npcRepository.npcStateFlow.value.apply {
            if (this.isOverlap(square).not()) {
                return@apply
            }

            return this.eventID
        }

        return EventType.None
    }

}
