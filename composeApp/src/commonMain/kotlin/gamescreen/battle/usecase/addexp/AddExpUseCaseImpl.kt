package gamescreen.battle.usecase.addexp

import core.repository.player.PlayerStatusRepository
import data.status.StatusRepository

class AddExpUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val statusRepository: StatusRepository,
) : AddExpUseCase {
    override fun invoke(exp: Int): Boolean {
        TODO("Not yet implemented")
    }
}
