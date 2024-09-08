package battle.service.attack

import common.status.Status

interface DecHpService {

    /**
     *  攻撃をして、攻撃後のステータスを返す
     */
    fun attack(
        target: Int,
        damage: Int,
        status: Status,
    ): Status
}
