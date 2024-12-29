package gamescreen.menu.item.tool.user

import data.item.tool.ToolRepository
import data.item.tool.ToolRepositoryImpl
import gamescreen.menu.domain.BagToolData
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import gamescreen.menu.repository.bag.BagRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class ToolUserViewModel : ItemUserViewModel(),
    KoinComponent {
    override val itemRepository: ToolRepository by inject()
    private val bagRepository: BagRepository by inject()

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

    override fun getPlayerNameAt(id: Int): String {
        return if (id < Constants.playerNum) {
            playerStatusRepository.getStatus(id).name
        } else {
            "バッグ"
        }
    }

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return if (id < Constants.playerNum) {
            playerStatusRepository.getStatus(id).toolList
        } else {
            bagRepository.getList().map {
                it.id
            }
        }
    }
}
