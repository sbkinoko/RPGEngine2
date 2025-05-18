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
                    statusData = playerStatus.statusData
                        .setHP(
                            value = Int.MAX_VALUE,
                        ).setMP(
                            value = Int.MAX_VALUE,
                        ),
                )
            )
        }
    }
}
