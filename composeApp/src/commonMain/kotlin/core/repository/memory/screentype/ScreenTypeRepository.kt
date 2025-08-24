package core.repository.memory.screentype

import gamescreen.GameScreenType
import kotlinx.coroutines.flow.StateFlow

interface ScreenTypeRepository {
    val screenStateFlow: StateFlow<GameScreenType>

    fun setScreenType(gameScreenType: GameScreenType)

    companion object {
        val INITIAL_SCREEN_TYPE: GameScreenType = GameScreenType.FIELD
    }
}
