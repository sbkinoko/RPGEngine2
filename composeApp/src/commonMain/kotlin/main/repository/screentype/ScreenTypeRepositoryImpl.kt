package main.repository.screentype

import kotlinx.coroutines.flow.Flow
import main.domain.ScreenType

class ScreenTypeRepositoryImpl : ScreenTypeRepository {
    override val screenTypeFlow: Flow<ScreenType>
        get() = TODO("Not yet implemented")

    override var screenType: ScreenType
        get() = TODO("Not yet implemented")
        set(value) {}
}
