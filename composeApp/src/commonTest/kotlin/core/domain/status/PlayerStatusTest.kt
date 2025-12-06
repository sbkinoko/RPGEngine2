package core.domain.status

import core.domain.status.job.Job
import core.domain.status.param.EXP

class PlayerStatusTest {
    companion object {
        val testPlayerStatus
            get() = PlayerStatus(
                toolList = listOf(),
                skillList = listOf(),
                exp = EXP(
                    listOf(
                        1,
                    )
                ),
                job = Job.Warrior,
            )

        val testActivePlayer
            get() = PlayerStatus(
                skillList = listOf(),
                toolList = listOf(),
                exp = EXP(
                    EXP.type1,
                ),
                job = Job.Warrior,
            )
    }
}
