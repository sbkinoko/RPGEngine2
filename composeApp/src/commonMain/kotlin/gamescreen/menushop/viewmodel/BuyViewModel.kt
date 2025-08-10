package gamescreen.menushop.viewmodel

import core.repository.money.MoneyRepository
import data.repository.item.tool.ToolId
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.amountdata.AmountData
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.flow.StateFlow
import values.TextData

class BuyViewModel(
    moneyRepository: MoneyRepository,
    amountData: AmountData,
    choiceRepository: ChoiceRepository,
    textRepository: TextRepository,
    shopMenuRepository: ShopMenuRepository,

    private val addToolUseCase: AddToolUseCase<ToolId>,
) : AbstractShopViewModel(
    amountData,
    moneyRepository,
    choiceRepository,
    textRepository,
    shopMenuRepository
) {
    override val amountText: String
        get() = TextData.SHOP_BUY

    override val shopItemStateFlow: StateFlow<List<ShopItem>>
        get() = shopMenuRepository.shopItemListStateFlow


    override fun setMax() {
        amountData.maxNum = if (shopItemList.size <= selectedIndex) {
            0
        } else {
            money / shopItemList[selectedIndex].price
        }
    }

    override fun decideNum(selected: Int) {
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
