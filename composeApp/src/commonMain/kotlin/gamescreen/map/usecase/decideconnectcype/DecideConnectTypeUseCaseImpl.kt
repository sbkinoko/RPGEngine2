package gamescreen.map.usecase.decideconnectcype

import core.domain.mapcell.CellType
import gamescreen.map.domain.ConnectType

class DecideConnectTypeUseCaseImpl : DecideConnectTypeUseCase {
    override fun invoke(
        cellList: List<List<CellType>>,
    ): ConnectType {
        if (cellList.size != 3) {
            throw RuntimeException()
        }

        cellList.forEach {
            if (it.size != 3) {
                throw RuntimeException()
            }
        }

        val centerId = cellList[1][1]

        val a0 = (cellList[0][1] == centerId).toInt()
        val a1 = (cellList[1][0] == centerId).toInt()
        val a2 = (cellList[1][2] == centerId).toInt()
        val a3 = (cellList[2][1] == centerId).toInt()

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
