package core.domain.status.param

class EXP(list: List<Int>) {

    private val totalList: List<Int>

    private var _level: Int = INITIAL_LEVEL
    val level: Int
        get() = _level

    var exp: Int = INITIAL_EXP
        set(value) {
            field = value
            _level = getLevel(value)
        }

    init {
        var sum = 0
        totalList = list.map { i ->
            sum += i
            sum
        }
    }

    private fun getLevel(
        totalExp: Int,
    ): Int {
        //現在の経験値で到達しているレベル
        totalList.mapIndexed { index, exp ->
            if (totalExp < exp) {
                return index + 1
            }
        }

        //最大レベル
        return totalList.size + 1
    }

    companion object {
        const val INITIAL_LEVEL = 0
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
