package gamescreen.map.usecase.decideconnectcype

import gamescreen.map.domain.ConnectType

class DecideConnectTypeUseCaseImpl : DecideConnectTypeUseCase {
    override fun invoke(array: Array<Array<Any>>): ConnectType {
        if (array.size != 3) {
            throw RuntimeException()
        }

        array.forEach {
            if (it.size != 3) {
                throw RuntimeException()
            }
        }

        val centerId = array[1][1]

        val a0 = (array[0][1] == centerId).toInt()
        val a1 = (array[1][0] == centerId).toInt()
        val a2 = (array[1][2] == centerId).toInt()
        val a3 = (array[2][1] == centerId).toInt()

        val sum = a0 + 2 * a1 + 4 * a2 + 8 * a3

        return when (sum) {
            1, 8, 9 -> ConnectType.Vertical
            2, 4, 6 -> ConnectType.Horizontal
            3 -> ConnectType.LeftTopUp
            5 -> ConnectType.RightToUp
            7 -> ConnectType.WithoutDown
            10 -> ConnectType.LeftTopDown
            11 -> ConnectType.WithoutRight
            12 -> ConnectType.RightToDown
            13 -> ConnectType.WithoutLeft
            14 -> ConnectType.WithoutUp
            15 -> ConnectType.Cross
            else -> throw RuntimeException()
        }
    }

    private fun Boolean.toInt(): Int = if (this) 1 else 0
}
