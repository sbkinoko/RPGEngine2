package battle.usecase.decmp

import common.repository.player.PlayerRepository

class DecPlayerMpUseCaseImpl(
    private val playerRepository: PlayerRepository
) : DecMpUseCase {

    override fun invoke(id: Int, amount: Int) {
        val player = playerRepository.getPlayer(id = id)
        val afterPlayer = player.copy(
            mp = player.mp.copy(
                value = player.mp.value - amount,
            )
        )
        playerRepository.setPlayer(
            id = id,
            status = afterPlayer,
        )
    }
}
