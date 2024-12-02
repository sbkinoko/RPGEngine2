package gamescreen.menu.usecase.gettoolid

import core.repository.player.PlayerStatusRepository
import gamescreen.menu.repository.bag.BagRepository
import values.Constants

class GetToolIdUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val bagRepository: BagRepository,
) : GetToolIdUseCase {
    override fun invoke(
        userId: Int,
        index: Int,
    ): Int {
        return if (userId < Constants.playerNum) {
            playerStatusRepository.getTool(
                userId,
                index,
            )
        } else {
            bagRepository.getItemIdAt(index)
        }
    }
}
