package core.domain.status

import core.domain.status.StatusDataTest.Companion.zeroStatus
import core.domain.status.param.EXP

class PlayerStatusTest {
    companion object {
        const val NAME = "TEST"

        val testPlayerStatus
            get() = PlayerStatus(
                statusData = zeroStatus,
                toolList = listOf(),
                skillList = listOf(),
                exp = EXP(
                    listOf(
                        1,
                    )
                )
            )

        val testActivePlayer
            get() = PlayerStatus(
                statusData = StatusDataTest.normalStatus,
                skillList = listOf(),
                toolList = listOf(),
                exp = EXP(
                    EXP.type1,
                ),
            )

        val testNotActivePlayer
            get() = testActivePlayer.run {
                copy(
                    statusData = statusData.copy(
                        hp = statusData.hp.copy(
                            value = 0,
                        )
                    )
                )
            }
    }
}
