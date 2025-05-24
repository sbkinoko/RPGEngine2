package core.domain.status

import core.domain.status.PlayerStatusTest.Companion.NAME
import core.domain.status.param.statusParameterWithMax.HP
import core.domain.status.param.statusParameterWithMax.MP

class StatusDataTest {

    companion object {
        private const val MAX_HP = 100
        private const val MAX_MP = 100

        val zeroStatus = StatusData(
            name = NAME,
            hp = HP(0),
            mp = MP(0),
        )

        val normalStatus = StatusData(
            name = "テスト",
            hp = HP(
                maxValue = MAX_HP,
            ),
            mp = MP(
                maxValue = MAX_MP,
            ),
        )
    }

}
