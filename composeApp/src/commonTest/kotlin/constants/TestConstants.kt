package constants

const val REPEAT_TIME = 2000
const val ACCEPT_RANGE = 0.2
const val BORDER_LOWER = ((1 - ACCEPT_RANGE) * REPEAT_TIME).toInt()
const val BORDER_UPPER = ((1 + ACCEPT_RANGE) * REPEAT_TIME).toInt()

/**
 * @param num   事象が起きた回数
 * @param prob 　事象が起きる確率(% 百分率)
 */
fun isInRange(
    num: Int,
    prob: Float,
): Boolean {
    return num in (BORDER_LOWER * prob / 100f).toInt()..(BORDER_UPPER * prob / 100f).toInt()
}
