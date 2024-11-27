package gamescreen.battle.usecase.getcontrollerbyscreentype

import controller.domain.ControllerCallback

interface GetControllerByCommandTypeUseCase {
    operator fun invoke(): ControllerCallback?
}
