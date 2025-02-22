package core.domain.item

sealed interface ItemKind : Item

interface Skill : ItemKind {
    val costType: CostType
}

interface Tool : ItemKind {
    // 繰り返し使える
    val isReusable: Boolean

    // 手放せる
    val isDisposable: Boolean
}
