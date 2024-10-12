package gamescreen.menu.item.tool.list

import core.domain.AbleType
import core.repository.item.tool.ToolRepository
import core.text.repository.TextRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.itemselect.ItemListViewModel
import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import org.koin.core.component.inject

class ToolListViewModel : ItemListViewModel() {
    override val itemRepository: ToolRepository by inject()
    private val textRepository: TextRepository by inject()
    private val useItemIdRepository: UseItemIdRepository by inject()

    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_LIST
    private val nextScreenType: MenuType
        get() = MenuType.TOOL_TARGET

    override fun goNextImpl() {
        val position = selectManager.selected
        val id = itemList[position]

        // fixme useCaseをつくる
        val ableType = AbleType.Able

        when (ableType) {
            AbleType.Able -> {
                // skillIdを保存
                useItemIdRepository.itemId = id
                //　次の画面に遷移
                commandRepository.push(
                    nextScreenType,
                )
            }

            AbleType.CANT_USE_BY_PLACE -> {
                textRepository.setText("ここでは使えません")
                textRepository.push(true)
            }

            // 道具はMP消費しない
            AbleType.CANT_USE_BY_MP -> Unit
        }
    }

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return playerRepository.getStatus(id).toolList
    }
}
