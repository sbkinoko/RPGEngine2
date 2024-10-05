package core.repository.item

import core.domain.item.Item

interface ItemRepository {
    fun getItem(id: Int): Item
}
