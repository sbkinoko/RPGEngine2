package core.domain.item

import core.domain.Place

interface Item {
    val id: Int
    val name: String
    val explain: String
    val targetNum: Int
    val usablePlace: Place
}

sealed interface ItemKind : Item {
    interface Skill : Item {
        val needMP: Int

        // fixme コストのタイプを作成する
        // HP MP　など
        val canUse: (Int) -> Boolean
    }

    interface Tool : Item {
        // 繰り返し使える
        val isReusable: Boolean

        // 手放せる
        val isDisposable: Boolean
    }
}

sealed interface TypeKind : Item {
    /**
     * 回復itemに必要な変数
     */
    interface HealItem : Item {
        /**
         * 回復量
         */
        val healAmount: Int

        /**
         * 回復対象にとれる状態
         */
        val targetType: TargetType
    }

    interface AttackItem : Item {
        val damageAmount: Int
    }

}
