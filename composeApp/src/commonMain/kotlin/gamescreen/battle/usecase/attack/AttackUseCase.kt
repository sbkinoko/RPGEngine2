package gamescreen.battle.usecase.attack

import core.domain.status.StatusData

interface AttackUseCase {
    suspend operator fun invoke(
        target: Int,
        attacker: StatusData,
    )
}
