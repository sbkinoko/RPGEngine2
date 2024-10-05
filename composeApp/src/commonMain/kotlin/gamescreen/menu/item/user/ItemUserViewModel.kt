package gamescreen.menu.item.user

import common.values.playerNum
import core.repository.item.ItemRepository
import core.repository.player.PlayerRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ItemUserViewModel : MenuChildViewModel(), KoinComponent {
    protected val playerRepository: PlayerRepository by inject()
    protected abstract val itemRepository: ItemRepository
    protected abstract val userRepository: UserRepository

    override val canBack: Boolean
        get() = true

    abstract val boundedScreenType: MenuType

    abstract val nextScreenType: MenuType

    override var selectManager: SelectManager =
        SelectManager(
            width = 1,
            itemNum = playerNum,
        )

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedScreenType
    }

    override fun goNextImpl() {
        // 選んだプレイヤーを保存
        userRepository.userId = selectManager.selected
        //　次の画面へ遷移
        commandRepository.push(nextScreenType)
    }

    fun getPlayerNameAt(id: Int): String {
        return playerRepository.getStatus(id).name
    }

    abstract fun getPlayerItemListAt(id: Int): List<Int>

    fun getItemName(id: Int): String {
        return itemRepository.getItem(id).name
    }
}
