package controller.domain

interface ControllerCallback {

    fun moveStick(
        stickPosition: StickPosition,
    )

    var pressA: () -> Unit

    var pressB: () -> Unit

    var pressM: () -> Unit

}
