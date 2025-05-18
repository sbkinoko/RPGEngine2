package core.usecase.heal

import core.repository.player.PlayerStatusRepository

class MaxHealUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
) : MaxHealUseCase {
    override suspend fun invoke() {
        playerStatusRepository.getPlayers().mapIndexed { index, playerStatus ->
            playerStatusRepository.setStatus(
                id = index,
                status = playerStatus.copy(
                    statusData = playerStatus.statusData.copy(
                        hp = playerStatus.statusData.hp.set(Int.MAX_VALUE),
                        mp = playerStatus.statusData.mp.set(Int.MAX_VALUE),
                    ),
                )
            )
        }
    }
}
