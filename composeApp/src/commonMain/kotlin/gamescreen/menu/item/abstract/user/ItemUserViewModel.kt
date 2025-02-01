package gamescreen.menu.item.abstract.user

import core.domain.item.Item
import core.repository.player.PlayerStatusRepository
import data.item.ItemRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.list.ItemList
import gamescreen.menu.item.repository.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ItemUserViewModel<T> : MenuChildViewModel(),
    ItemList<T>,
    KoinComponent {
    private val userRepository: UserRepository by inject()
    protected val playerStatusRepository: PlayerStatusRepository by inject()
    protected abstract val itemRepository: ItemRepository<T>

    abstract val boundedScreenType: MenuType

    abstract val nextScreenType: MenuType

    abstract val playerNum: Int

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedScreenType
    }

    override fun goNextImpl() {
        // 選んだプレイヤーを保存
        userRepository.userId = selectManager.selected
        //　次の画面へ遷移
        commandRepository.push(nextScreenType)
    }

    abstract fun getPlayerNameAt(id: Int): String

    override fun getItemName(id: T): String {
        return itemRepository.getItem(id).name
    }

    override fun getPlayerItemListAt(id: Int): List<Item> {
        return getPlayerItemIdListAt(id).map {
            itemRepository.getItem(it)
        }
    }
}
