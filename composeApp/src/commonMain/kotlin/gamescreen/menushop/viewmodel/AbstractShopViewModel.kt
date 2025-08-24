package gamescreen.menushop.viewmodel

import androidx.compose.runtime.mutableStateOf
import common.DefaultScope
import controller.domain.Stick
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.menu.SelectableChildViewModel
import core.repository.memory.money.MoneyRepository
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.SelectManager
import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.ShopType
import gamescreen.menushop.domain.SubWindowType
import gamescreen.menushop.domain.amountdata.AmountData
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

abstract class AbstractShopViewModel(
    val amountData: AmountData,
    protected val moneyRepository: MoneyRepository,
    protected val choiceRepository: ChoiceRepository,
    protected val textRepository: TextRepository,
    protected val shopMenuRepository: ShopMenuRepository,
) : KoinComponent,
    SelectableChildViewModel<Int>() {

    abstract val shopItemStateFlow: StateFlow<List<ShopItem>>

    abstract val amountText: String

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = 1,
        )
    )

    val isShopMenuVisibleStateFlow =
        shopMenuRepository.shopTypeStateFlow

    val moneyFlow = moneyRepository.moneyStateFLow

    val subWindowType = mutableStateOf(
        SubWindowType.EXPLAIN,
    )

    protected var money = 0
    protected var selectedIndex = 0
    protected var shopItemList = emptyList<ShopItem>()

    init {
        DefaultScope.launch {
            // fixme 初期化タイミングを調べる
            delay(500)
            selectCore.stateFlow.collect {
                selectedIndex = it
                setMax()
            }
        }

        DefaultScope.launch {
            // fixme 初期化タイミングを調べる
            delay(500)
            moneyRepository.moneyStateFLow.collect {
                money = it
                setMax()
            }
        }

        DefaultScope.launch {
            // fixme 初期化タイミングを調べる
            delay(500)
            shopItemStateFlow.collect {
                shopItemList = it
                (selectCore as SelectCoreInt).changeItemNum(it.size)
                setMax()
            }
        }
    }

    abstract fun setMax()

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
        shopMenuRepository.setShopType(ShopType.CLOSE)
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
                decideNum(
                    selected = selectedIndex,
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

    abstract fun decideNum(selected: Int)
}
