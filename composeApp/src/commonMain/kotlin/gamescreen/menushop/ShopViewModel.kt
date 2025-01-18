package gamescreen.menushop

import core.menu.SelectableChildViewModel
import gamescreen.menu.domain.SelectManager
import gamescreen.menushop.repoisitory.ShopMenuRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShopViewModel : KoinComponent,
    SelectableChildViewModel<Any>() {

    override var selectManager: SelectManager =
        SelectManager(
            width = 1,
            itemNum = 5,
        )


    private val shopMenuRepository: ShopMenuRepository by inject()
    val isShopMenuVisibleStateFlow =
        shopMenuRepository.isVisibleStateFlow

    fun hideMenu() {
        shopMenuRepository.setVisibility(
            isVisible = false,
        )
    }

    override fun goNext() {
        //NOP
    }

    override fun pressA() {
        //NOP
    }

    override fun pressB() {
        hideMenu()
    }

}
