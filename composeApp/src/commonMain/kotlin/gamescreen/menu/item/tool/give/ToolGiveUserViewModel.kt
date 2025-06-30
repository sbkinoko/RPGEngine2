package gamescreen.menu.item.tool.give

import core.ToolBagRepositoryName
import core.domain.item.BagItemData
import core.domain.item.Tool
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.bag.BagRepository
import core.repository.statusdata.StatusDataRepository
import data.item.tool.ToolId
import data.item.tool.ToolRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.GiveResult
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.usecase.givetool.GiveToolUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class ToolGiveUserViewModel(
    private val statusDataRepository: StatusDataRepository,
) : ItemUserViewModel<ToolId, Tool>(),
    KoinComponent {
    override val itemRepository: ToolRepository by inject()
    private val bagRepository: BagRepository<ToolId> by inject(
        qualifier = ToolBagRepositoryName,
    )
    private val targetRepository: TargetRepository by inject()

    private val choiceRepository: ChoiceRepository by inject()
    private val textRepository: TextRepository by inject()

    private val giveToolUseCase: GiveToolUseCase by inject()

    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_GIVE
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

    // fixme 初期化用のuseCaseを作る
    // 別の場所で呼び出す
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

    override fun goNextImpl() {
        targetRepository.target = selectCore.stateFlow.value
        choiceRepository.push(
            listOf(
                Choice(
                    text = "渡す",
                    callBack = {
                        CoroutineScope(Dispatchers.Main).launch {
                            when (val result = giveToolUseCase.invoke()) {
                                is GiveResult.OK -> {
                                    textRepository.push(
                                        TextBoxData(
                                            text = "道具を渡しました",
                                            callBack = {
                                                commandRepository.pop()
                                            }
                                        ),
                                    )
                                }

                                is GiveResult.NG -> {
                                    textRepository.push(
                                        TextBoxData(
                                            text = result.text
                                        )
                                    )
                                }
                            }
                        }
                    }
                ),
                Choice(
                    text = "他の人に渡す",
                    callBack = {}
                ),
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
