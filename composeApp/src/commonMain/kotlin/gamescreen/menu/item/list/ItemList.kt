package gamescreen.menu.item.list

import gamescreen.menu.domain.BagToolData

interface ItemList {
    fun getPlayerItemListAt(id: Int): List<Int>

    fun getItemName(id: Int): String
}

interface BagItemList {
    val bagItem: List<BagToolData>
    fun getItemName(id: Int): String
}
