package gamescreen.battle.repository.flash

data class FlashInfo(
    val count: Int,
) {
    val isFlashing = count > 0

    val isVisible: Boolean
        get() = count % 2 == 0
}
