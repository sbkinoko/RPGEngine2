package core.domain.item

import core.domain.Place

interface Item {
    val name: String
    val explain: String
}

interface NeedTarget {
    /**
     * 対象が敵か味方か
     */
    val targetType: TargetType

    /**
     * 対象にとれるステータスの状態
     */
    val targetStatusType: TargetStatusType

    /**
     * 対象にとる数
     */
    val targetNum: Int
}

interface UsableItem : Item {
    val usablePlace: Place

    // fixme リストで持つようにすれば処理の簡略化ができそう
    // コストの種類が増えた時に楽できる
    val costType: CostType
}
