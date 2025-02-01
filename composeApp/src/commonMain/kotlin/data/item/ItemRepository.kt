package data.item

import core.domain.item.Item

interface ItemRepository<T> {
    fun getItem(id: T): Item
}
