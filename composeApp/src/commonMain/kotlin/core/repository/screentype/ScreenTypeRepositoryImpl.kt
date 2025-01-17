package core.repository.screentype

import core.domain.ScreenType
import core.repository.screentype.ScreenTypeRepository.Companion.INITIAL_SCREEN_TYPE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScreenTypeRepositoryImpl : ScreenTypeRepository {
    private val mutableScreenTypeStateFlow =
        MutableStateFlow(
            INITIAL_SCREEN_TYPE
        )

    override val screenStateFlow: StateFlow<ScreenType>
        get() = mutableScreenTypeStateFlow.asStateFlow()

    override fun setScreenType(screenType: ScreenType) {
        mutableScreenTypeStateFlow.value = screenType
    }
}
