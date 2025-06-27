package core.domain.status

import core.domain.status.param.StatusParameterWithMax

class StatusDataTest {

    companion object {
        private const val MAX_HP = 100
        private const val MAX_MP = 100
        private const val HP = 10

        val name = "テスト"

        val TestPlayerStatusActive = StatusData(
            name = name,
            hp = StatusParameterWithMax(
                maxPoint = MAX_HP,
            ),
            mp = StatusParameterWithMax(
                maxPoint = MAX_MP,
            ),
        )

        val TestPlayerStatusInActive = TestPlayerStatusActive.setHP(0)

        val TestEnemyStatusActive = StatusData(
            name = name,
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
