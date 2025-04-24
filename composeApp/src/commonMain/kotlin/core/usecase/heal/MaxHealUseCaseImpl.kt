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

                        hp = playerStatus.statusData.hp.run {
                            copy(
                                value = maxValue,
                            )
                        },
                        mp = playerStatus.statusData.mp.run {
                            copy(
                                value = maxValue,
                            )
                        }
                    ),
                )
            )
        }
    }
}
