package gamescreen.mapshop

import core.menu.SelectableChildViewModel
import gamescreen.mapshop.repoisitory.ShopMenuRepository
import gamescreen.menu.domain.SelectManager
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
