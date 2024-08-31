package battle.usecase.decmp

import common.repository.player.PlayerRepository

class DecMpUseCaseImpl(
    private val playerRepository: PlayerRepository,
) : DecMpUseCase {

    override fun invoke(
        playerId: Int,
        amount: Int
    ) {
        val player = playerRepository.getPlayer(id = playerId)
        val afterPlayer = player.copy(
            mp = player.mp.copy(
                value = player.mp.value - amount,
            )
        )
        playerRepository.setPlayer(
            id = playerId,
            status = afterPlayer,
        )
    }
}
