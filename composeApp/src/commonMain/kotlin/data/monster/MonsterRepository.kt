package data.monster

import core.domain.status.StatusData
import core.domain.status.monster.MonsterStatus

interface MonsterRepository {

    fun getMonster(id: Int): Pair<MonsterStatus, StatusData>
}
