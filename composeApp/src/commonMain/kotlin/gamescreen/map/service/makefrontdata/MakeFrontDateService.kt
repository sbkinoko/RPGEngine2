package gamescreen.map.service.makefrontdata

import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.background.FrontObjectData

interface MakeFrontDateService {
    operator fun invoke(
        backgroundData: BackgroundData,
        player: Player,
    ): FrontObjectData
}
