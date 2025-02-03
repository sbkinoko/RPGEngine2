package gamescreen.map.usecase.moveplayer

import gamescreen.map.domain.Player

interface PlayerMoveUseCase {
    suspend operator fun invoke(
        player: Player,
    )
}
