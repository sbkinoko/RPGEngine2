package common.status

import main.status.MonsterStatus
import main.status.param.HP
import main.status.param.MP

class MonsterStatusTest {
    companion object {
        private const val MAX_HP = 10
        private const val MAX_MP = 10

        fun getMonster() = MonsterStatus(
            imgId = 1,
            name = "テスト",
            hp = HP(
                maxValue = MAX_HP,
            ),
            mp = MP(
                maxValue = MAX_MP,
            )
        )

        // HPが0のモンスターを作成
        fun getNotActiveMonster() = getMonster().copy(
            hp = HP(
                maxValue = MAX_MP,
                value = 0,
            )
        )
    }
}
