package core.usecase.changetomap

import core.domain.ScreenType
import core.repository.screentype.ScreenTypeRepository

class ChangeToMapUseCaseImpl(
    private val screenTypeRepository: ScreenTypeRepository,
) : ChangeToMapUseCase {

    override fun invoke() {
        screenTypeRepository.setScreenType(
            screenType = ScreenType.FIELD,
        )
    }
}
