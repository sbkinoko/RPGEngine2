package gamescreen.menushop

import androidx.compose.runtime.mutableStateOf
import core.menu.SelectableChildViewModel
import core.repository.money.MoneyRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menushop.amountdata.AmountData
import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.SubWindowType
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShopViewModel(
    moneyRepository: MoneyRepository,
    val amountData: AmountData,
    val choiceRepository: ChoiceRepository,
    val textRepository: TextRepository,
    val addToolUseCase: AddToolUseCase,
) : KoinComponent,
    SelectableChildViewModel<Any>() {

    override var selectManager: SelectManager =
        SelectManager(
            width = 1,
            itemNum = 5,
        )

    val shopItem = mutableStateOf(
        List(2) {
            ShopItem(
                name = "アイテム${it + 1}",
                value = (it + 1) * (it + 1) * 100,
                explain = "アイテム${it + 1}の説明",
                // fixme 正しいidを入れる
                itemId = it,
            )
        }
    )

    private val shopMenuRepository: ShopMenuRepository by inject()
    val isShopMenuVisibleStateFlow =
        shopMenuRepository.isVisibleStateFlow

    val moneyFlow = moneyRepository.moneyStateFLow

    val subWindowType = mutableStateOf(
        SubWindowType.EXPLAIN,
    )

    fun hideMenu() {
        shopMenuRepository.setVisibility(
            isVisible = false,
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
        val itemId = shopItem.value[selected].itemId

        choiceRepository.push(
            commandType = listOf(
                Choice(
                    text = "買う",
                    callBack = {
                        addToolUseCase.invoke(
                            toolId = itemId,
                            toolNum = amountData.num,
                        )
                        textRepository.push(
                            TextBoxData(
                                text = "まいどあり",
                            )
                        )
                    },
                ),
                Choice(
                    text = "やめる",
                    callBack = {},
                )
            )
        )
    }
}
