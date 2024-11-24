package core.domain.item.skill

import core.domain.item.Item

sealed interface Skill : Item {
    val needMP: Int

    // fixme コストのタイプを作成する
    // HP MP　など
    val canUse: (Int) -> Boolean
}
