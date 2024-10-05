package gamescreen.menu.item.itemselect

import core.repository.item.ItemRepository
import core.repository.player.PlayerRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.ItemList
import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import gamescreen.menu.item.repository.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ItemListViewModel : MenuChildViewModel(),
    ItemList,
    KoinComponent {
    protected val playerRepository: PlayerRepository by inject()
    protected abstract val userRepository: UserRepository
    protected abstract val itemRepository: ItemRepository

    override var selectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    override val canBack: Boolean
        get() = true

    val userId: Int
        get() = userRepository.userId

    protected abstract val itemList: List<Int>

    protected abstract val boundedScreenType: MenuType

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedScreenType
    }

    fun init() {
        loadItem()
    }

    private fun loadItem() {
        selectManager = SelectManager(
            width = 1,
            itemNum = itemList.size
        )
        selectManager.selected = INIT_ITEM_POSITION
    }

    fun getExplainAt(position: Int): String {
        val itemId = itemList[position]
        return itemRepository.getItem(itemId).explain
    }

    override fun pressB() {
        commandRepository.pop()
    }

    override fun getItemName(id: Int): String {
        return itemRepository.getItem(id).name
    }

    companion object {
        private const val INIT_ITEM_POSITION = 0
    }
}
