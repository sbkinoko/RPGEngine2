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
    }
}
