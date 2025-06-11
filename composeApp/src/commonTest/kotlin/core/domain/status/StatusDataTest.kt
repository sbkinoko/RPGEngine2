package core.domain.status

import core.domain.status.PlayerStatusTest.Companion.NAME
import core.domain.status.param.StatusParameterWithMax

class StatusDataTest {

    companion object {
        private const val MAX_HP = 100
        private const val MAX_MP = 100

        val zeroStatus = StatusData(
            name = NAME,
            hp = StatusParameterWithMax(0),
            mp = StatusParameterWithMax(0),
        )

        val normalStatus = StatusData(
            name = "テスト",
            hp = StatusParameterWithMax(
                maxPoint = MAX_HP,
            ),
            mp = StatusParameterWithMax(
                maxPoint = MAX_MP,
            ),
        )
    }
}
