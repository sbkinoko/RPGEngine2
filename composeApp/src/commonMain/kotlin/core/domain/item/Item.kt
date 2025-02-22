package core.domain.item

import core.domain.Place

interface Item {
    val name: String
    val explain: String
    val targetNum: Int
    val usablePlace: Place
    val costType: CostType
}
