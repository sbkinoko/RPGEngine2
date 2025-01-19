package gamescreen.menushop.repoisitory.shopmenu

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShopMenuRepositoryImpl : ShopMenuRepository {
    private val mutableIsVisibleStateFlow =
        MutableStateFlow(false)

    override val isVisibleStateFlow: StateFlow<Boolean> =
        mutableIsVisibleStateFlow.asStateFlow()

    override fun setVisibility(isVisible: Boolean) {
        mutableIsVisibleStateFlow.value = isVisible
    }
}
