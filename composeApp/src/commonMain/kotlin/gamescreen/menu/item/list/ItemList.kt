package gamescreen.menu.item.list

import core.domain.item.Item

interface ItemList<T> {
    fun getPlayerItemIdListAt(id: Int): List<T>

    fun getPlayerItemListAt(id: Int): List<Item>

    fun getItemName(id: T): String
}
