package gamescreen.battle.repository.attackeffect

import androidx.compose.ui.graphics.Path

data class AttackEffectInfo(
    val count: Int = 0,
) {
    val isVisible: Boolean
        get() = count > 0

    fun getPath(
        center: Float,
        maxHeight: Float,
    ): Path {
        val index = rateList.size + 1 - count

        // 範囲外の場合、何もないパスを返す
        if (index < 0 || rateList.size <= index) {
            return Path()
        }

        val rate = rateList[index]
        return Path().apply {
            moveTo(center + 40f, 0f)
            lineTo(
                center - 40 + 80 * (1 - rate),
                maxHeight * rate
            )
            lineTo(center + 80f, 40f)
            close()
        }
    }
}

val rateList = listOf(
    0f,
    0.5f,
    0.6f,
    0.7f,
    0.8f,
    0.9f,
    1f,
    1f,
)
