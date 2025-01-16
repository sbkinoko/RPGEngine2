package gamescreen.mapshop

import gamescreen.mapshop.repoisitory.ShopMenuRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShopViewModel : KoinComponent {

    private val shopMenuRepository: ShopMenuRepository by inject()
    val isShopMenuVisibleStateFlow =
        shopMenuRepository.isVisibleStateFlow

    fun hideMenu() {
        shopMenuRepository.setVisibility(
            isVisible = false,
        )
    }

}
