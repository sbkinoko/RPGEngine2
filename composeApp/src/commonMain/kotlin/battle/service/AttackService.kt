package battle.service

import common.status.MonsterStatus

interface AttackService {

    /**
     *  攻撃をして、攻撃後のステータスを返す
     */
    fun attack(
        target: Int,
        damage: Int,
        monster: MonsterStatus,
    ): MonsterStatus
}
