package core.domain.item

sealed interface ItemKind

interface Skill : ItemKind, Item

interface Tool : ItemKind, Item {

    // 手放せる
    val isDisposable: Boolean
}

interface Equipment : ItemKind, Item
