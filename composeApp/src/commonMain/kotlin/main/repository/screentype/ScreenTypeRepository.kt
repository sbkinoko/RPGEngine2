package main.repository.screentype

import kotlinx.coroutines.flow.MutableSharedFlow
import main.domain.ScreenType

interface ScreenTypeRepository {
    val screenTypeFlow: MutableSharedFlow<ScreenType>

    var screenType: ScreenType

    companion object {
        val INITIAL_SCREEN_TYPE: ScreenType = ScreenType.FIELD
    }
}
