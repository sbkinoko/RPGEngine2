package gamescreen.menu.item.abstract.itemselect

import core.domain.AbleType
import core.domain.TextBoxData
import core.repository.item.ItemRepository
import core.repository.player.PlayerRepository
import core.text.repository.TextRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.list.ItemList
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ItemListViewModel : MenuChildViewModel(),
    ItemList,
    KoinComponent {
    private val menuStateRepository: MenuStateRepository by inject()
    private val textRepository: TextRepository by inject()

    private val userRepository: UserRepository by inject()
    private val useItemIdRepository: UseItemIdRepository by inject()
    private val indexRepository: IndexRepository by inject()

    protected val playerRepository: PlayerRepository by inject()
    protected abstract val itemRepository: ItemRepository

    override var selectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    override val canBack: Boolean
        get() = true

    val userId: Int
        get() = userRepository.userId

    protected val itemList: List<Int>
        get() = getPlayerItemListAt(userId)

    protected abstract val boundedScreenType: MenuType
    protected abstract val nextScreenType: MenuType

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedScreenType
    }

    fun init() {
        selectManager = SelectManager(
            width = 1,
            itemNum = itemList.size
        )
        // fixme 前回の選択位置を利用する
        selectManager.selected = INIT_ITEM_POSITION
    }

    open fun getExplainAt(position: Int): String {
        val itemId = itemList[position]
        return itemRepository.getItem(itemId).explain
    }

    override fun pressB() {
        commandRepository.pop()
    }

    override fun getItemName(id: Int): String {
        return itemRepository.getItem(id).name
    }

    override fun goNextImpl() {
        val ableType = getAbleType()

        when (ableType) {
            AbleType.Able -> {
                // itemIdを保存
                useItemIdRepository.itemId = itemList[selectManager.selected]
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
