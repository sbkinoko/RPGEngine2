package gamescreen.battle.service.attackcalc

import core.domain.item.DamageType
import core.domain.status.StatusData

interface AttackCalcService {
    operator fun invoke(
        attacker: StatusData,
        attacked: StatusData,
        damageType: DamageType,
    ): StatusData
}
