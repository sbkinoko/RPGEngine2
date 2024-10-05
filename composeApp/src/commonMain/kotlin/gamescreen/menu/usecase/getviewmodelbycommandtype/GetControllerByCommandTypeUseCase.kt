package gamescreen.menu.usecase.getviewmodelbycommandtype

import controller.domain.ControllerCallback

interface GetControllerByCommandTypeUseCase {
    operator fun invoke(): ControllerCallback?
}
