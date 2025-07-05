package gamescreen.menu.item.tool.user

import core.ToolBagRepositoryName
import core.domain.item.BagItemData
import core.domain.item.Tool
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.bag.BagRepository
import core.repository.statusdata.StatusDataRepository
import data.item.tool.ToolId
import data.item.tool.ToolRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class ToolUserViewModel(
    private val statusDataRepository: StatusDataRepository,
) : ItemUserViewModel<ToolId, Tool>(),
    KoinComponent {
    override val itemRepository: ToolRepository by inject()
    private val bagRepository: BagRepository<ToolId> by inject(
        qualifier = ToolBagRepositoryName,
    )

    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_USER
    override val nextScreenType: MenuType
        get() = MenuType.TOOL_LIST

    override val playerNum: Int
        get() = Constants.playerNum + 1

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = playerNum,
        )
    )

    // fixme 初期化タイミングを考える
    // toolGiveUseViewModelでも同じことをしている
    init {
        bagRepository.setData(
            data = BagItemData(
                id = ToolId.HEAL1,
                num = 100,
            )
        )
        bagRepository.setData(
            data = BagItemData(
                id = ToolId.HEAL2,
                num = 100,
            )
        )
    }

    override fun getPlayerNameAt(id: Int): String {
        return if (id < Constants.playerNum) {
            statusDataRepository.getStatusData(id).name
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
