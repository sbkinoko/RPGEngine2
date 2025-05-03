package gamescreen.map.usecase.save

import gamescreen.map.domain.Player

interface SaveUseCase {

    fun save(
        player: Player,
    )
}
