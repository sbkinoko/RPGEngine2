package main.repository.screentype

import kotlinx.coroutines.flow.Flow
import main.domain.ScreenType

interface ScreenTypeRepository {
    val screenTypeFlow: Flow<ScreenType>

    var screenType: ScreenType

    companion object {
        val INITIAL_SCREEN_TYPE: ScreenType = ScreenType.FIELD
    }
}
