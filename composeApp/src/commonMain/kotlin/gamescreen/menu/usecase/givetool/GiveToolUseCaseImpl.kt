package gamescreen.menu.usecase.givetool

import core.repository.player.PlayerStatusRepository
import gamescreen.menu.domain.GiveResult
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.menu.repository.bag.BagRepository
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menu.usecase.bag.dectool.DecToolUseCase
import values.Constants
import values.TextData

class GiveToolUseCaseImpl(
    private val targetRepository: TargetRepository,
    private val userRepository: UserRepository,
    private val indexRepository: IndexRepository,
    private val bagRepository: BagRepository,
    private val playerStatusRepository: PlayerStatusRepository,

    private val decToolUseCase: DecToolUseCase,
    private val addToolUseCase: AddToolUseCase,
) : GiveToolUseCase {
    override suspend fun invoke(): GiveResult {
        if (targetRepository.target < Constants.playerNum) {
            val targetPlayer = playerStatusRepository.getPlayers()[targetRepository.target]
            if (targetPlayer.toolList.size >= Constants.MAX_TOOL_NUM) {
                return GiveResult.NG(
                    TextData.HAS_FULL_ITEM
                )
            }
        }

        val itemId: Int
        if (userRepository.userId < Constants.playerNum) {
            val player = playerStatusRepository.getPlayers()[userRepository.userId]
            itemId = player.toolList[indexRepository.index]

            playerStatusRepository.setStatus(
                id = userRepository.userId,
                status = player.copy(
                    // 渡した位置の道具を削除
                    toolList = player.toolList.filterIndexed { index, _ ->
                        index != indexRepository.index
                    }
                )
            )
        } else {
            val bag = bagRepository.getList()
            itemId = bag[indexRepository.index].id
            decToolUseCase.invoke(
                itemId = itemId,
                itemNum = 1,
            )
        }

        if (targetRepository.target < Constants.playerNum) {
            val player = playerStatusRepository.getPlayers()[targetRepository.target]
            playerStatusRepository.setStatus(
                id = targetRepository.target,
                status = player.copy(
                    toolList = player.toolList + itemId
                ),
            )
        } else {
            addToolUseCase.invoke(
                toolId = itemId,
                toolNum = 1,
            )
        }

        return GiveResult.OK(
            itemId = itemId,
        )
    }
}