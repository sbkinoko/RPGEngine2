package core.repository.screentype

import core.domain.ScreenType
import kotlinx.coroutines.flow.StateFlow

interface ScreenTypeRepository {
    val screenStateFlow: StateFlow<ScreenType>

    fun setScreenType(screenType: ScreenType)

    companion object {
        val INITIAL_SCREEN_TYPE: ScreenType = ScreenType.FIELD
    }
}
