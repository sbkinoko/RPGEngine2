package gamescreen.menushop

import androidx.compose.runtime.mutableStateOf
import core.menu.SelectableChildViewModel
import core.repository.money.MoneyRepository
import gamescreen.menu.domain.SelectManager
import gamescreen.menushop.amountdata.AmountData
import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.SubWindowType
import gamescreen.menushop.repoisitory.shopmenu.ShopMenuRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShopViewModel(
    moneyRepository: MoneyRepository,
    val amountData: AmountData,
) : KoinComponent,
    SelectableChildViewModel<Any>() {

    override var selectManager: SelectManager =
        SelectManager(
            width = 1,
            itemNum = 5,
        )


    val shopItem = mutableStateOf(
        List(5) {
            ShopItem(
                name = "アイテム${it + 1}",
                value = (it + 1) * (it + 1) * 100,
                explain = "アイテム${it + 1}の説明"
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


    fun buy() {

    }


}
