package gamescreen.menushop.repoisitory

import kotlinx.coroutines.flow.StateFlow

interface ShopMenuRepository {
    val isVisibleStateFlow: StateFlow<Boolean>

    fun setVisibility(
        isVisible: Boolean,
    )
}
