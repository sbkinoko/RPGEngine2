package battle.service.attack

import common.status.MonsterStatus

class UpdateMonsterStatusService : UpdateParameterService<MonsterStatus> {
    override fun decHP(
        amount: Int,
        status: MonsterStatus,
    ): MonsterStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value - amount
            )
        )
    }

    override fun incHP(
        amount: Int,
        status: MonsterStatus,
    ): MonsterStatus {
        TODO("Not yet implemented")
    }

    override fun decMP(
        amount: Int,
        status: MonsterStatus,
    ): MonsterStatus {
        TODO("Not yet implemented")
    }

    override fun incMP(
        amount: Int,
        status: MonsterStatus,
    ) {
        TODO("Not yet implemented")
    }
}
