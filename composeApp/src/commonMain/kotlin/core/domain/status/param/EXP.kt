package core.domain.status.param

import values.TextData.Companion.NEED_EXP_MAX_LEVEL

data class EXP(
    val list: List<Int>,
    val value: Int = INITIAL_EXP,
) {

    private val totalList: List<Int>

    val level: Int


    val needExp: String
        get() {
            if (totalList.size + 1 <= level) {
                return NEED_EXP_MAX_LEVEL
            }

            return (totalList[level - 1] - value).toString()
        }

    init {
        var sum = 0
        totalList = list.map { i ->
            sum += i
            sum
        }
        level = calcLevel()
    }

    private fun calcLevel(): Int {
        //現在の経験値で到達しているレベル
        totalList.mapIndexed { index, exp ->
            if (this.value < exp) {
                return index + 1
            }
        }

        //最大レベル
        return totalList.size + 1
    }

    companion object {
        const val INITIAL_LEVEL = 1
        const val INITIAL_EXP = 0

        val type1 = listOf(
            10,
            15,
            30,
            50,
            100,
        )
    }
}
