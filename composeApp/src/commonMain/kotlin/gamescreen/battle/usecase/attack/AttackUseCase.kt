package gamescreen.battle.usecase.attack

import core.domain.item.DamageType
import core.domain.status.StatusData

interface AttackUseCase {
    suspend operator fun invoke(
        target: Int,
        attacker: StatusData,
        damageType: DamageType,
    )
}
