package gamescreen.menu.item.tool.give

import common.values.Constants
import core.confim.repository.ChoiceRepository
import core.domain.Choice
import core.repository.item.tool.ToolRepository
import core.repository.item.tool.ToolRepositoryImpl
import gamescreen.menu.domain.BagToolData
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import gamescreen.menu.repository.bag.BagRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ToolGiveUserViewModel : ItemUserViewModel(),
    KoinComponent {
    override val itemRepository: ToolRepository by inject()
    private val bagRepository: BagRepository by inject()
    private val choiceRepository: ChoiceRepository by inject()

    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_USER
    override val nextScreenType: MenuType
        get() = MenuType.TOOL_LIST

    override val playerNum: Int
        get() = Constants.playerNum + 1

    override var selectManager: SelectManager =
        SelectManager(
            width = 1,
            itemNum = playerNum,
        )

    init {
        bagRepository.setData(
            data = BagToolData(
                id = ToolRepositoryImpl.HEAL_TOOL,
                num = 100,
            )
        )
        bagRepository.setData(
            data = BagToolData(
                id = ToolRepositoryImpl.HEAL_TOOL2,
                num = 100,
            )
        )
    }

    override fun goNextImpl() {
        choiceRepository.push(
            listOf(
                Choice(
                    text = "渡す",
                    callBack = {
                        choiceRepository.pop()
                        commandRepository.pop()
                    }
                ),
                Choice(
                    text = "他の人に渡す",
                    callBack = {
                        choiceRepository.pop()
                    }
                ),
            )
        )
    }

    override fun getPlayerNameAt(id: Int): String {
        return if (id < Constants.playerNum) {
            playerRepository.getStatus(id).name
        } else {
            "バッグ"
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
