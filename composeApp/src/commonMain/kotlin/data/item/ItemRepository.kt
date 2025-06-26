package data.item

interface ItemRepository<T, V> {
    fun getItem(id: T): V
}
