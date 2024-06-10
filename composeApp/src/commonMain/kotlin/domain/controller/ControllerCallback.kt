package domain.controller

interface ControllerCallback {

    fun moveStick(
        dx: Float,
        dy: Float,
    )

    var pressB: () -> Unit
}
