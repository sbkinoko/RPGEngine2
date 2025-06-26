package core.domain.item

sealed interface ItemKind

interface Skill : ItemKind, UsableItem

interface Tool : ItemKind, UsableItem {

    // 手放せる
    val isDisposable: Boolean
}

interface Equipment : ItemKind, Item
