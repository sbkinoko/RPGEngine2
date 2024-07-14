package controller.domain

interface ControllerCallback {

    fun moveStick(
        dx: Float,
        dy: Float,
    )

    var pressA: () -> Unit

    var pressB: () -> Unit

    var pressM: () -> Unit

}
