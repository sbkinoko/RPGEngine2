package gamescreen.menu.item.tool.user

import data.item.tool.ToolId
import data.item.tool.ToolRepository
import gamescreen.menu.domain.BagToolData
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import gamescreen.menu.repository.bag.BagRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class ToolUserViewModel : ItemUserViewModel<ToolId>(),
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


    // fixme 初期化タイミングを考える
    // toolGiveUseViewModelでも同じことをしている
    init {
        bagRepository.setData(
            data = BagToolData(
                id = ToolId.HEAL1,
                num = 100,
            )
        )
        bagRepository.setData(
            data = BagToolData(
                id = ToolId.HEAL2,
                num = 100,
            )
        )
    }

    override fun getPlayerNameAt(id: Int): String {
        return if (id < Constants.playerNum) {
            playerStatusRepository.getStatus(id).statusData.name
        } else {
            "バッグ"
        }
    }

    override fun getPlayerItemIdListAt(id: Int): List<ToolId> {
        return if (id < Constants.playerNum) {
            playerStatusRepository.getStatus(id).toolList
        } else {
            bagRepository.getList().map {
                it.id
            }
        }
    }
}
