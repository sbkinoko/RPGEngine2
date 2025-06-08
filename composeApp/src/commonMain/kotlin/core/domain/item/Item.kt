package core.domain.item

import core.domain.Place

interface Item {
    val name: String
    val explain: String
    val targetNum: Int
    val usablePlace: Place
    val targetType: TargetType

    // fixme リストで持つようにすれば処理の簡略化ができそう
    // コストの種類が増えた時に楽できる
    val costType: CostType

    /**
     * 対象にとれるステータスの状態
     */
    val targetStatusType: TargetStatusType
}
