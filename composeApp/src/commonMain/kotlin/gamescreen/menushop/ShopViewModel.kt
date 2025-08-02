package gamescreen.menushop

import androidx.compose.runtime.mutableStateOf
import common.DefaultScope
import controller.domain.Stick
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.menu.SelectableChildViewModel
import core.repository.money.MoneyRepository
import data.item.tool.ToolId
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.SubWindowType
import gamescreen.menushop.domain.amountdata.AmountData
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ShopViewModel(
    val moneyRepository: MoneyRepository,
    val amountData: AmountData,
    val choiceRepository: ChoiceRepository,
    val textRepository: TextRepository,
    val addToolUseCase: AddToolUseCase<ToolId>,
    private val shopMenuRepository: ShopMenuRepository,
) : KoinComponent,
    SelectableChildViewModel<Int>() {

    val shopItemStateFlow = shopMenuRepository.shopItemListStateFlow

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = 1,
        )
    )

    val isShopMenuVisibleStateFlow =
        shopMenuRepository.isVisibleStateFlow

    val moneyFlow = moneyRepository.moneyStateFLow

    val subWindowType = mutableStateOf(
        SubWindowType.EXPLAIN,
    )

    private var money = 0
    private var selected = 0
    private var shopItemList = emptyList<ShopItem>()

    init {
        DefaultScope.launch {
            selectCore.stateFlow.collect {
                selected = it
                setMax()
            }
        }

        DefaultScope.launch {
            moneyRepository.moneyStateFLow.collect {
                money = it
                setMax()
            }
        }

        DefaultScope.launch {
            shopItemStateFlow.collect {
                shopItemList = it
                (selectCore as SelectCoreInt).changeItemNum(it.size)
                setMax()
            }
        }

        moneyRepository.setMoney(1000)
    }

    private fun setMax() {
        amountData.maxNum = if (shopItemList.size <= selected) {
            0
        } else {
            money / shopItemList[selected].price
        }
    }

    fun reset() {
        amountData.reset()
    }

    fun getExplainAt(selected: Int): String {
        if (shopItemList.size <= selected) {
            return ""
        }

        return shopItemList[selected].name + "の説明"
    }

    fun hideMenu() {
        shopMenuRepository.setList(emptyList())
        textRepository.push(
            TextBoxData("また来てね")
        )
    }

    override fun goNext() {
        when (
            subWindowType.value
        ) {
            SubWindowType.EXPLAIN -> {
                subWindowType.value = SubWindowType.AMOUNT
            }

            SubWindowType.AMOUNT -> {
                buy(
                    selected = selected,
                )
            }
        }
    }

    override fun moveStick(stick: Stick) {
        when (subWindowType.value) {
            SubWindowType.EXPLAIN -> super.moveStick(stick)
            SubWindowType.AMOUNT -> {
                timer.callbackIfTimePassed {
                    amountData.useStick(stick.toCommand())
                }
            }
        }
    }

    override fun pressB() {
        when (
            subWindowType.value
        ) {
            SubWindowType.EXPLAIN -> hideMenu()
            SubWindowType.AMOUNT -> {
                subWindowType.value = SubWindowType.EXPLAIN
            }
        }
    }

    fun buy(selected: Int) {
        val itemId = shopItemList[selected].itemId
        val price = shopItemList[selected].price

        choiceRepository.push(
            commandType = listOf(
                Choice(
                    text = "買う",
                    callBack = {
                        addToolUseCase.invoke(
                            toolId = itemId,
                            toolNum = amountData.num,
                        )
                        moneyRepository.decMoney(
                            price * amountData.num
                        )
                        textRepository.push(
                            TextBoxData(
                                text = "まいどあり",
                                callBack = {
                                    pressB()
                                }
                            ),
                        )
                    },
                ),
                Choice(
                    text = "やめる",
                )
            )
        )
    }
}
