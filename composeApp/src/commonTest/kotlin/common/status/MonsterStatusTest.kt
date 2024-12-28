package common.status

import core.domain.status.MonsterStatus
import core.domain.status.param.HP
import core.domain.status.param.MP

class MonsterStatusTest {
    companion object {
        private const val MAX_HP = 10
        private const val MAX_MP = 10

        fun getTestMonster() = MonsterStatus(
            imgId = 1,
            name = "テスト",
            hp = HP(
                maxValue = MAX_HP,
            ),
            mp = MP(
                maxValue = MAX_MP,
            ),
            money = 1,
            exp = 1,
        )

        // HPが0のモンスターを作成
        fun getNotActiveTestMonster() = getTestMonster().copy(
            hp = HP(
                maxValue = MAX_MP,
                value = 0,
            )
        )
    }
}
