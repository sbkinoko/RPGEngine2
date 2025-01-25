package core.domain.mapcell

import values.event.BoxId

// fixme いい命名を考える
sealed class CellType {

    //whenのelseを使わないようにしたいが、全列挙していると数が多くなってしまう
    //対策として列挙の数を減らすためのマーカーインターフェースを作成

    /**
     * 全身が入ったらイベントが起きるマス
     */
    sealed interface EventCell

    /**
     * 当たり判定を持つを持つマス
     */
    sealed interface CollisionCell

    /**
     * オブジェクト画像を持つマス
     */
    sealed interface ObjectCell


    data object Glass : CellType()
    data object Water : CollisionCell, CellType()

    data object Town1I : EventCell, CellType()
    data object Town1O : EventCell, CellType()


    data object Road : CellType()
    class Box(
        val id: BoxId,
    ) : CollisionCell,
        ObjectCell,
        CellType()

    data object Null : CellType()

    class TextCell(
        val id: Int,
    ) : CellType()
}
