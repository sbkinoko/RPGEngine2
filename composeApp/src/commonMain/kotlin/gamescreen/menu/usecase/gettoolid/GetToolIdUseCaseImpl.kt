package gamescreen.menu.usecase.gettoolid

import core.repository.bag.BagRepository
import core.repository.player.PlayerStatusRepository
import data.item.tool.ToolId
import values.Constants

class GetToolIdUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val bagRepository: BagRepository<ToolId>,
) : GetToolIdUseCase {
    override fun invoke(
        userId: Int,
        index: Int,
    ): ToolId {
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
