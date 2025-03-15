package gamescreen.map.usecase.collision.iscollidedevent

import gamescreen.map.domain.collision.square.Rectangle
import values.event.EventType

interface IsCollidedEventUseCase {
    /**
     * イベントオブジェクトの内部にいるかどうかをチェック
     */
    operator fun invoke(
        playerSquare: Rectangle,
    ): EventType?
}
