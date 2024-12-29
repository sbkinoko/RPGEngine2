package data.monster

import core.domain.status.MonsterStatus
import core.domain.status.param.HP
import core.domain.status.param.MP

class MonsterRepositoryImpl : MonsterRepository {
    override fun getMonster(id: Int): MonsterStatus {
        // fixme モンスターの種類を増やす
        return MonsterStatus(
            1, "花",
            hp = HP(
                maxValue = 10,
            ),
            mp = MP(
                maxValue = 10,
            ),
            exp = 2,
            money = 1,
        )
    }
}
