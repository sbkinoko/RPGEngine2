package gamescreen.menu.item

interface ItemList {
    fun getPlayerItemListAt(id: Int): List<Int>

    fun getItemName(id: Int): String
}
