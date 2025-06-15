package gamescreen.battle.service.attackcalc

import core.domain.item.DamageType
import core.domain.status.StatusData
import core.domain.status.StatusType

interface AttackCalcService {
    operator fun <T : StatusType> invoke(
        attacker: StatusData<*>,
        attacked: StatusData<T>,
        damageType: DamageType,
    ): StatusData<T>
}
