package gamescreen.menu.item.abstract.user

import core.domain.item.Item
import core.repository.character.player.PlayerCharacterRepository
import data.repository.item.ItemRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.list.ItemList
import gamescreen.menu.item.repository.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ItemUserViewModel<T, V : Item> : MenuChildViewModel(),
    ItemList<T>,
    KoinComponent {
    private val userRepository: UserRepository by inject()
    protected val playerStatusRepository: PlayerCharacterRepository by inject()
    protected abstract val itemRepository: ItemRepository<T, V>

    abstract val boundedScreenType: MenuType

    abstract val nextScreenType: MenuType

    abstract val playerNum: Int

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedScreenType
    }

    override fun goNextImpl() {
        // 選んだプレイヤーを保存
        userRepository.userId = selectCore.stateFlow.value
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
