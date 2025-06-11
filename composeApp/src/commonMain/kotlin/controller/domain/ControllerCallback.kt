package controller.domain

interface ControllerCallback {

    fun moveStick(
        stick: Stick,
    )

    fun pressA()

    fun pressB()

    fun pressM()
}
