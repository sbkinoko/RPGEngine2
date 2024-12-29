package gamescreen.menu.item.tool.give

import data.item.tool.ToolRepository
import data.item.tool.ToolRepositoryImpl
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.BagToolData
import gamescreen.menu.domain.GiveResult
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.repository.bag.BagRepository
import gamescreen.menu.usecase.givetool.GiveToolUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class ToolGiveUserViewModel : ItemUserViewModel(),
    KoinComponent {
    override val itemRepository: ToolRepository by inject()
    private val bagRepository: BagRepository by inject()
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
        targetRepository.target = selectManager.selected
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
