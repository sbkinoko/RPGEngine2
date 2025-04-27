package core.repository.screentype

import core.repository.screentype.ScreenTypeRepository.Companion.INITIAL_SCREEN_TYPE
import gamescreen.GameScreenType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScreenTypeRepositoryImpl : ScreenTypeRepository {
    private val mutableScreenTypeStateFlow =
        MutableStateFlow(
            INITIAL_SCREEN_TYPE
        )

    override val screenStateFlow: StateFlow<GameScreenType>
        get() = mutableScreenTypeStateFlow.asStateFlow()

    override fun setScreenType(gameScreenType: GameScreenType) {
        mutableScreenTypeStateFlow.value = gameScreenType
    }
}
