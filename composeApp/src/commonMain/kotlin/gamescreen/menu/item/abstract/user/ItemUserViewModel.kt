package gamescreen.menu.item.abstract.user

import core.repository.item.ItemRepository
import core.repository.player.PlayerStatusRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.list.ItemList
import gamescreen.menu.item.repository.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ItemUserViewModel : MenuChildViewModel(),
    ItemList,
    KoinComponent {
    private val userRepository: UserRepository by inject()
    protected val playerStatusRepository: PlayerStatusRepository by inject()
    protected abstract val itemRepository: ItemRepository

    override val canBack: Boolean
        get() = true

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

    override fun getItemName(id: Int): String {
        return itemRepository.getItem(id).name
    }
}
