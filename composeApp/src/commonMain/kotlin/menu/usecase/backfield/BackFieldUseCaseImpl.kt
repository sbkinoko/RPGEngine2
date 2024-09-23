package menu.usecase.backfield

import core.domain.ScreenType
import main.screentype.ScreenTypeRepository
import menu.repository.menustate.MenuStateRepository

class BackFieldUseCaseImpl(
    private val screenTypeRepository: ScreenTypeRepository,
    private val menuStateRepository: MenuStateRepository,
) : BackFieldUseCase {
    override fun invoke() {
        screenTypeRepository.screenType = ScreenType.FIELD
        menuStateRepository.reset()
    }
}
