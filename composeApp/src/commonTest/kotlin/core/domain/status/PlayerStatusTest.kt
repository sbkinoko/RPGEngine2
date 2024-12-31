package core.domain.status

import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP

class PlayerStatusTest {
    companion object {
        const val NAME = "TEST"

        val testPlayerStatus
            get() = PlayerStatus(
                name = NAME,
                hp = HP(0),
                mp = MP(0),
                toolList = listOf(),
                skillList = listOf(),
                exp = EXP(
                    listOf(
                        1,
                    )
                )
            )

        private const val MAX_HP = 100
        private const val MAX_MP = 100

        val testActivePlayer
            get() = PlayerStatus(
                name = "テスト",
                hp = HP(
                    maxValue = MAX_HP,
                ),
                mp = MP(
                    maxValue = MAX_MP,
                ),
                skillList = listOf(),
                toolList = listOf(),
                exp = EXP(
                    EXP.type1,
                ),
            )

        val testNotActivePlayer
            get() = testActivePlayer.copy(
                hp = HP(
                    maxValue = MAX_HP,
                    value = 0,
                ),
            )
    }
}
