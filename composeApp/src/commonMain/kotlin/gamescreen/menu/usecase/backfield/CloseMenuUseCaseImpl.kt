package gamescreen.menu.usecase.backfield

import core.usecase.changetomap.ChangeToMapUseCase
import gamescreen.menu.repository.menustate.MenuStateRepository

class CloseMenuUseCaseImpl(
    private val menuStateRepository: MenuStateRepository,

    private val changeToMapUseCase: ChangeToMapUseCase,
) : CloseMenuUseCase {
    override fun invoke() {
        changeToMapUseCase.invoke()
        menuStateRepository.reset()
    }
}
