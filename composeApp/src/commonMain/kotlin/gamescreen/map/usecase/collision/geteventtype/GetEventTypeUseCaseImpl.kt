package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.EventObject
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.domain.npc.NPCData
import values.event.EventType

// todo テスト作る
class GetEventTypeUseCaseImpl : GetEventTypeUseCase {

    override fun invoke(
        rectangle: Rectangle,
        backgroundData: BackgroundData,
        npcData: NPCData,
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

        npcData.npcList.forEach { npc ->
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
