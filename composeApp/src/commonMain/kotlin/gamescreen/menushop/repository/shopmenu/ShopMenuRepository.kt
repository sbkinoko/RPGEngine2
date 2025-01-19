package gamescreen.menushop.repository.shopmenu

import kotlinx.coroutines.flow.StateFlow

interface ShopMenuRepository {
    val isVisibleStateFlow: StateFlow<Boolean>

    fun setVisibility(
        isVisible: Boolean,
    )
}
