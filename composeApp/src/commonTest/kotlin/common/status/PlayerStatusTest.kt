package common.status

import core.domain.status.PlayerStatus
import core.domain.status.param.HP
import core.domain.status.param.MP

class PlayerStatusTest {

    companion object {
        private const val MAX_HP = 100
        private const val MAX_MP = 100

        fun getPlayer() = PlayerStatus(
            name = "テスト",
            hp = HP(
                maxValue = MAX_HP,
            ),
            mp = MP(
                maxValue = MAX_MP,
            ),
            skillList = listOf(),
            toolList = listOf(),
        )

        fun getInActivePlayer() = getPlayer().copy(
            hp = HP(
                maxValue = MAX_HP,
                value = 0,
            ),
        )
    }
}
