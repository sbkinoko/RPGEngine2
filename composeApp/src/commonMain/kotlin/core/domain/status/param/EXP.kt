package core.domain.status.param

class EXP(list: List<Int>) {

    private val totalList: List<Int>

    private var _level: Int = INITIAL_LEVEL
    val level: Int
        get() = _level

    var exp: Int = INITIAL_EXP
        set(value) {
            field = value
            _level = calcLevel()
        }

    val needExp: String
        get() {
            if (totalList.size + 1 <= level) {
                return "最大レベル"
            }

            return (totalList[level - 1] - exp).toString()
        }

    init {
        var sum = 0
        totalList = list.map { i ->
            sum += i
            sum
        }
        exp = INITIAL_EXP
    }

    private fun calcLevel(
    ): Int {
        //現在の経験値で到達しているレベル
        totalList.mapIndexed { index, exp ->
            if (this.exp < exp) {
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
