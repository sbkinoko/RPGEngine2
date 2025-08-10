package gamescreen.menu.item.tool.list

import core.ToolBagRepositoryName
import core.domain.AbleType
import core.domain.item.Tool
import core.repository.bag.BagRepository
import data.repository.monster.item.tool.ToolId
import data.repository.monster.item.tool.ToolRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.itemselect.ItemListViewModel
import gamescreen.menu.usecase.usetoolinmap.UseItemInMapUseCase
import org.koin.core.component.inject
import values.Constants

class ToolListViewModel(
    useItemInMapUseCase: UseItemInMapUseCase,
) : ItemListViewModel<ToolId, Tool>(
    useItemInMapUseCase = useItemInMapUseCase,
) {
    override val itemRepository: ToolRepository by inject()
    private val bagRepository: BagRepository<ToolId> by inject(
        qualifier = ToolBagRepositoryName,
    )

    private val choiceRepository: ChoiceRepository by inject()

    override val selectedItem: Tool
        get() = getPlayerItemListAt(userId)[selectedFlowState.value]

    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_LIST
    override val nextScreenType: MenuType
        get() = MenuType.TOOL_TARGET

    override fun getAbleType(): AbleType {
        return AbleType.Able
    }

    override fun goNextImpl() {
        choiceRepository.push(
            listOf(
                Choice(
                    text = "使う",
                    callBack = {
                        super.goNextImpl()
                    }
                ),
                Choice(
                    text = "渡す",
                    callBack = {
                        goGive()
                    }
                ),
                Choice(
                    text = "キャンセル",
                    callBack = { }
                )
            ))
    }

    private fun goGive() {
        // indexを保存
        indexRepository.index = selectCore.stateFlow.value
        //　次の画面に遷移
        menuStateRepository.push(
            MenuType.TOOL_GIVE,
        )
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
