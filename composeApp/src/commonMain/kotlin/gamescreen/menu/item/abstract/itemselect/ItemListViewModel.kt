package gamescreen.menu.item.abstract.itemselect

import core.domain.AbleType
import core.domain.item.Item
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.item.ItemRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.list.ItemList
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.menu.repository.menustate.MenuStateRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ItemListViewModel<T> : MenuChildViewModel(),
    ItemList<T>,
    KoinComponent {
    protected val menuStateRepository: MenuStateRepository by inject()
    private val textRepository: TextRepository by inject()

    private val userRepository: UserRepository by inject()
    protected val indexRepository: IndexRepository by inject()

    protected val playerStatusRepository: PlayerStatusRepository by inject()
    protected val statusDataRepository: StatusDataRepository by inject()

    protected abstract val itemRepository: ItemRepository<T>

    override var selectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    val userId: Int
        get() = userRepository.userId

    protected val itemIdList: List<T>
        get() = getPlayerItemIdListAt(userId)

    protected val itemList: List<Item>
        get() = getPlayerItemListAt(userId)

    protected abstract val boundedScreenType: MenuType
    protected abstract val nextScreenType: MenuType

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedScreenType
    }

    fun init() {
        selectManager = SelectManager(
            width = 1,
            itemNum = itemIdList.size
        )
        // fixme 前回の選択位置を利用する
        selectManager.selected = INIT_ITEM_POSITION
    }

    open fun getExplainAt(position: Int): String {
        val itemId = itemIdList[position]
        return itemRepository.getItem(itemId).explain
    }

    override fun pressB() {
        commandRepository.pop()
    }

    override fun getItemName(id: T): String {
        return itemRepository.getItem(id).name
    }

    override fun getPlayerItemListAt(id: Int): List<Item> {
        return getPlayerItemIdListAt(id).map {
            itemRepository.getItem(it)
        }
    }

    override fun goNextImpl() {
        val ableType = getAbleType()

        when (ableType) {
            AbleType.Able -> {
                // indexを保存
                indexRepository.index = selectManager.selected
                //　次の画面に遷移
                menuStateRepository.push(
                    nextScreenType,
                )
            }

            AbleType.CANT_USE_BY_PLACE -> {
                textRepository.push(
                    TextBoxData(
                        "ここでは使えません",
                    )
                )
            }

            AbleType.CANT_USE_BY_MP -> {
                textRepository.push(
                    TextBoxData(
                        "MPがたりません",
                    )
                )
            }
        }
    }

    protected abstract fun getAbleType(): AbleType

    companion object {
        private const val INIT_ITEM_POSITION = 0
    }
}
