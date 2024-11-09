package gamescreen.menu.domain

sealed class GiveResult {

    class OK(val itemId: Int) : GiveResult()

    class NG(val text: String) : GiveResult()
}
