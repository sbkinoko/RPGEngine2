package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.EventObject
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.repository.npc.NPCRepository
import values.event.EventType

// todo テスト作る
class GetEventTypeUseCaseImpl(
    private val npcRepository: NPCRepository,
) : GetEventTypeUseCase {

    override fun invoke(
        rectangle: Rectangle,
        backgroundData: BackgroundData,
    ): EventType {
        backgroundData.fieldData.forEach { rowArray ->
            rowArray.forEach cell@{ cell ->
                val collisionList = cell.collisionData

                // 物がないので次を探索
                if (collisionList.isEmpty()
                ) {
                    return@cell
                }

                collisionList.forEach shape@{ shape ->
                    // 重なってないので次へ
                    if (shape.isOverlap(
                            rectangle,
                            cell.baseX,
                            cell.baseY,
                        ).not()
                    ) {
                        return@shape
                    }

                    // イベントオブジェではないので次へ
                    if (shape !is EventObject) {
                        return@shape
                    }

                    //　イベントオブジェなのでIDを返す
                    return shape.eventType
                }
            }
        }

        npcRepository.npcStateFlow.value.forEach { npc ->
            npc.eventRectangle.let {
                if (it.isOverlap(rectangle).not()) {
                    return@forEach
                }

                return it.eventType
            }
        }

        return EventType.None
    }
}
