package gamescreen.menu.item.abstract.target

import core.domain.item.Item
import data.repository.monster.item.ItemRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.item.repository.user.UserRepository
import org.koin.core.component.inject

abstract class TargetViewModel<T, V : Item> : MenuChildViewModel() {
    protected val userRepository: UserRepository by inject()

    val user: Int
        get() = userRepository.userId

    abstract fun canSelect(target: Int): Boolean


    protected abstract val itemRepository: ItemRepository<T, V>

    abstract val itemId: T

    val explain: String
        get() = itemRepository.getItem(itemId).explain
}
