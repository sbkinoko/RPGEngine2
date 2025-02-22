package core.domain.item

sealed interface ItemKind : Item

interface Skill : ItemKind

interface Tool : ItemKind {

    // 手放せる
    val isDisposable: Boolean
}
