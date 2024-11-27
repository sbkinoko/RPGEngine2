package core.repository.screentype

import core.domain.ScreenType
import kotlinx.coroutines.flow.MutableSharedFlow

interface ScreenTypeRepository {
    // fixme stateFlowにする
    val screenTypeFlow: MutableSharedFlow<ScreenType>

    var screenType: ScreenType

    companion object {
        val INITIAL_SCREEN_TYPE: ScreenType = ScreenType.FIELD
    }
}
