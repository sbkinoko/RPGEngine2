package menu.usecase.backfield

import main.domain.ScreenType
import main.repository.screentype.ScreenTypeRepository
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
