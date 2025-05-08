package core.usecase.changetomap

import core.repository.screentype.ScreenTypeRepository
import gamescreen.GameScreenType

class ChangeToMapUseCaseImpl(
    private val screenTypeRepository: ScreenTypeRepository,
) : ChangeToMapUseCase {

    override fun invoke() {
        screenTypeRepository.setScreenType(
            gameScreenType = GameScreenType.FIELD,
        )
    }
}
