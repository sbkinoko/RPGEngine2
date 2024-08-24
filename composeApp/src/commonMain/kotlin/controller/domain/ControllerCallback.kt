package controller.domain

interface ControllerCallback {

    fun moveStick(
        stickPosition: StickPosition,
    )

    fun pressA()

    fun pressB()

    fun pressM()

}
