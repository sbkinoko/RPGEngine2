package gamescreen.menu.usecase.gettoolid

import core.repository.memory.bag.BagRepository
import data.repository.item.tool.ToolId
import values.Constants

class GetToolIdUseCaseImpl(
    private val playerStatusRepository: core.repository.memory.character.player.PlayerCharacterRepository,
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
