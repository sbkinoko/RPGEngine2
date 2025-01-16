package gamescreen.mapshop.repoisitory

import kotlinx.coroutines.flow.StateFlow

class ShopMenuRepositoryImpl : ShopMenuRepository {
    override val isVisibleStateFlow: StateFlow<Boolean>
        get() = TODO("Not yet implemented")

    override fun setVisibility(isVisible: Boolean) {
        TODO("Not yet implemented")
    }
}
