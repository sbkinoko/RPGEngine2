package gamescreen.battle.usecase.attack

import core.domain.item.DamageType
import core.domain.status.StatusData
import core.domain.status.StatusType

interface AttackUseCase<T : StatusType> {
    suspend operator fun invoke(
        target: Int,
        attacker: StatusData<T>,
        damageType: DamageType,
    )
}
