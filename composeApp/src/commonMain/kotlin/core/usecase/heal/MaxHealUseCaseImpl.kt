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
                    hp = playerStatus.hp.run {
                        copy(
                            value = maxValue,
                        )
                    },
                    mp = playerStatus.mp.run {
                        copy(
                            value = maxValue,
                        )
                    }
                )
            )
        }
    }
}
