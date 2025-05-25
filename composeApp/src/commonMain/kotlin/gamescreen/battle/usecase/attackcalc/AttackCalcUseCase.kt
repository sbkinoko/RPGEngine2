package gamescreen.battle.usecase.attackcalc

import core.domain.status.StatusData

interface AttackCalcUseCase {
    operator fun invoke(
        attacker: StatusData,
        attacked: StatusData,
    ): StatusData
}
