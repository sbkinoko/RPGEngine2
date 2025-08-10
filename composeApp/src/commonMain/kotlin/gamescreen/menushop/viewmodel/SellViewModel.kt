package gamescreen.menushop.viewmodel

import core.repository.bag.BagRepository
import core.repository.money.MoneyRepository
import data.repository.monster.item.tool.ToolId
import data.repository.monster.item.tool.ToolRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.usecase.bag.dectool.DecItemUseCase
import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.amountdata.AmountData
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class SellViewModel(
    moneyRepository: MoneyRepository,
    amountData: AmountData,
    choiceRepository: ChoiceRepository,
    textRepository: TextRepository,
    shopMenuRepository: ShopMenuRepository,

    private val bagRepository: BagRepository<ToolId>,
    private val toolRepository: ToolRepository,
    private val decToolUseCaseImpl: DecItemUseCase<ToolId>,
) : AbstractShopViewModel(
    amountData,
    moneyRepository,
    choiceRepository,
    textRepository,
    shopMenuRepository
) {
    override val amountText: String
        get() = "売る"

    private val mutableShopItemFlow = MutableStateFlow<List<ShopItem>>(
        emptyList()
    )

    override val shopItemStateFlow: StateFlow<List<ShopItem>>
        get() = mutableShopItemFlow.asStateFlow()


    fun updateBag() {
        mutableShopItemFlow.value =
            bagRepository.getList().map {
                ShopItem(
                    name = toolRepository.getItem(it.id).name + "×" + it.num,
                    price = 100,
                    explain = toolRepository.getItem(it.id).explain,
                    itemId = it.id,
                )
            }
    }

    override fun setMax() {
        if (bagRepository.getList().isEmpty()) {
            amountData.maxNum = 0
        } else {
            amountData.maxNum = bagRepository.getList()[selectedIndex].num
        }
    }

    override fun decideNum(selected: Int) {
        val itemId = shopItemList[selected].itemId
        val price = shopItemList[selected].price

        choiceRepository.push(
            commandType = listOf(
                Choice(
                    text = "売る",
                    callBack = {
                        decToolUseCaseImpl.invoke(
                            itemId = itemId,
                            itemNum = amountData.num,
                        )
                        moneyRepository.addMoney(
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
