package core.domain.status

import core.domain.status.PlayerStatusTest.Companion.NAME
import core.domain.status.param.StatusParameterWithMax

class StatusDataTest {

    companion object {
        private const val MAX_HP = 100
        private const val MAX_MP = 100
        private const val HP = 10

        val zeroStatus = StatusData<StatusType.Player>(
            name = NAME,
            hp = StatusParameterWithMax(0),
            mp = StatusParameterWithMax(0),
        )

        val TestPlayerStatusActive = StatusData<StatusType.Player>(
            name = "テスト",
            hp = StatusParameterWithMax(
                maxPoint = MAX_HP,
            ),
            mp = StatusParameterWithMax(
                maxPoint = MAX_MP,
            ),
        )

        val TestPlayerStatusInActive = TestPlayerStatusActive.setHP(0)

        val TestEnemyStatusActive = StatusData<StatusType.Enemy>(
            name = "テスト",
            hp = StatusParameterWithMax(
                maxPoint = MAX_HP,
                point = HP
            ),
            mp = StatusParameterWithMax(
                maxPoint = MAX_MP,
            ),
        )

        val TestEnemyStatusInActive = TestEnemyStatusActive.setHP(0)
    }
}
