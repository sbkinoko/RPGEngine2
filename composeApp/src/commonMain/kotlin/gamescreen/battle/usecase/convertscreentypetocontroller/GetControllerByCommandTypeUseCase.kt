package battle.usecase.convertscreentypetocontroller

import controller.domain.ControllerCallback

interface GetControllerByCommandTypeUseCase {
    operator fun invoke(): ControllerCallback?
}
