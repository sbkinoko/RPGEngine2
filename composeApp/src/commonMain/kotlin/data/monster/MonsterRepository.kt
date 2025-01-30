package data.monster

import core.domain.status.monster.MonsterStatus

interface MonsterRepository {

    fun getMonster(id: Int): MonsterStatus

}
