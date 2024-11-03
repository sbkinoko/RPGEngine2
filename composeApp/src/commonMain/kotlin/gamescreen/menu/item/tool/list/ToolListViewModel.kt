package gamescreen.menu.item.tool.list

import common.values.Constants
import core.confim.repository.ConfirmRepository
import core.domain.AbleType
import core.domain.Choice
import core.repository.choice.ChoiceRepository
import core.repository.item.tool.ToolRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.itemselect.ItemListViewModel
import gamescreen.menu.repository.bag.BagRepository
import org.koin.core.component.inject

class ToolListViewModel : ItemListViewModel() {
    override val itemRepository: ToolRepository by inject()
    private val bagRepository: BagRepository by inject()
    private val choiceRepository: ChoiceRepository by inject()
    private val confirmRepository: ConfirmRepository by inject()

    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_LIST
    override val nextScreenType: MenuType
        get() = MenuType.TOOL_TARGET

    override fun getAbleType(): AbleType {
        return AbleType.Able
    }

    override fun goNextImpl() {
        choiceRepository.choiceList = listOf(
            Choice(
                text = "使う",
                callBack = {
                    super.goNextImpl()
                    confirmRepository.pop()
                }
            ),
            Choice(
                text = "キャンセル",
                callBack = {
                    confirmRepository.pop()
                }
            )
        )
        confirmRepository.push(true)
    }

    override fun getExplainAt(position: Int): String {
        val txt = super.getExplainAt(position)
        return if (userId < Constants.playerNum) {
            txt
        } else {
            "所持数 : " +
                    bagRepository.getList()[position].num.toString() +
                    "\n" +
                    txt
        }
    }

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return if (id < Constants.playerNum) {
            playerRepository.getStatus(id).toolList
        } else {
            bagRepository.getList().map {
                it.id
            }
        }
    }
}
