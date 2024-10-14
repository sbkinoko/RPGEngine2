package core.domain.item.skill

import core.domain.item.Item

sealed interface Skill : Item {
    val needMP: Int

    // fixme コストのタイプをする
    val canUse: (Int) -> Boolean
}
