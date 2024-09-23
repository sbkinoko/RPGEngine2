package main.screentype

import core.domain.ScreenType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import main.screentype.ScreenTypeRepository.Companion.INITIAL_SCREEN_TYPE

class ScreenTypeRepositoryImpl : ScreenTypeRepository {
    override val screenTypeFlow: MutableSharedFlow<ScreenType> = MutableSharedFlow(replay = 1)

    private var _screenType: ScreenType = INITIAL_SCREEN_TYPE

    override var screenType: ScreenType
        get() = _screenType
        set(value) {
            _screenType = value
            CoroutineScope(Dispatchers.Default).launch {
                screenTypeFlow.emit(value)
            }
        }
}
