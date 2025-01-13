package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.collision.EventObject
import gamescreen.map.domain.collision.Square
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.npc.NPCRepository
import gamescreen.map.usecase.collision.list.GetCollisionListUseCase
import values.EventType

// todo テスト作る
class GetEventTypeUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
    private val getCollisionListUseCase: GetCollisionListUseCase,
    private val npcRepository: NPCRepository,
) : GetEventTypeUseCase {

    override fun invoke(
        square: Square,
    ): EventType {
        backgroundRepository.backgroundStateFlow.value.forEach { rowArray ->
            rowArray.forEach cell@{ cell ->
                val collisionList = getCollisionListUseCase.invoke(
                    backgroundCell = cell,
                )
                // 物がないので次を探索
                if (collisionList.isEmpty()
                ) {
                    return@cell
                }

                collisionList.forEach shape@{ shape ->
                    // 重なってないので次へ
                    if (shape.isOverlap(square).not()) {
                        return@shape
                    }

                    // イベントオブジェではないので次へ
                    if (shape !is EventObject) {
                        return@shape
                    }

                    //　イベントオブジェなのでIDを返す
                    return shape.eventID
                }
            }
        }

        npcRepository.npcStateFlow.value.forEach {
            if (it.isOverlap(square).not()) {
                return@forEach
            }

            return it.eventID
        }

        return EventType.None
    }
}
