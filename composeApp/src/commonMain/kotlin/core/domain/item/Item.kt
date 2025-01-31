package core.domain.item

import core.domain.Place

interface Item {
    val id: Int
    val name: String
    val explain: String
    val targetNum: Int
    val usablePlace: Place
}
