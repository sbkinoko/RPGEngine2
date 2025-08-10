package data.repository.item

interface ItemRepository<T, V> {
    fun getItem(id: T): V
}
