package core.domain.item

sealed interface ItemKind : Item

interface Skill : ItemKind {
    val needMP: Int

    // fixme コストのタイプを作成する
    // HP MP　など
    val canUse: (Int) -> Boolean
}

interface Tool : ItemKind {
    // 繰り返し使える
    val isReusable: Boolean

    // 手放せる
    val isDisposable: Boolean
}
