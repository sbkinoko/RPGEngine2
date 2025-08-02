package gamescreen.menushop.viewmodel

import core.repository.money.MoneyRepository
import data.item.tool.ToolId
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menushop.domain.amountdata.AmountData
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.text.repository.TextRepository


class SellViewModel(
    moneyRepository: MoneyRepository,
    amountData: AmountData,
    choiceRepository: ChoiceRepository,
    textRepository: TextRepository,
    addToolUseCase: AddToolUseCase<ToolId>,
    shopMenuRepository: ShopMenuRepository,
) : AbstractShopViewModel(
    moneyRepository,
    amountData,
    choiceRepository,
    textRepository,
    addToolUseCase,
    shopMenuRepository
)
