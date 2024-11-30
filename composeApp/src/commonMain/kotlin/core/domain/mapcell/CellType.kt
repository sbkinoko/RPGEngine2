package core.domain.mapcell

import gamescreen.map.data.BoxId

sealed class CellType {


    /**
     * 全身が入ったらイベントが起きるマス
     */
    // どうにもならなくなったらinterfaceにすることも視野に入れておく
    sealed class EventCell : CellType()

    /**
     * 当たり判定を持つオブジェクトを持つマス
     */
    // どうにもならなくなったらinterfaceにすることも視野に入れておく
    sealed class CollisionCell : CellType()

    data object Glass : CellType()
    data object Water : CollisionCell()

    data object Town1I : EventCell()
    data object Town1O : EventCell()


    data object Road : CellType()
    class Box(
        val id: BoxId,
    ) : CollisionCell()

    data object Null : CellType()

    class TextCell(
        val id: Int,
    ) : CellType()
}
