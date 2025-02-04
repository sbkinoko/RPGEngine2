package gamescreen.map.usecase.moveplayer

import gamescreen.map.domain.Player

// fixme 削除する
interface PlayerMoveUseCase {
    suspend operator fun invoke(
        player: Player,
    )
}
