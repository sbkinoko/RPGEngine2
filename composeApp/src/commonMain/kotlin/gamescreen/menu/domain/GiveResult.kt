package gamescreen.menu.domain

sealed class GiveResult {

    data class OK(val itemId: Int) : GiveResult()

    data class NG(val text: String) : GiveResult()
}
