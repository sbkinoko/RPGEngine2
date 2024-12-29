package data.monster

import core.domain.status.MonsterStatus

interface MonsterRepository {

    fun getMonster(id: Int): MonsterStatus

}
