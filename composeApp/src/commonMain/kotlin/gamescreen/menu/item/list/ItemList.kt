package gamescreen.menu.item.list

interface ItemList {
    fun getPlayerItemListAt(id: Int): List<Int>

    fun getItemName(id: Int): String
}
