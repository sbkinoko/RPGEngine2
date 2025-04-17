package gamescreen.map.service.makefrontdata

import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.background.ObjectData

interface MakeFrontDateService {
    operator fun invoke(
        backgroundData: BackgroundData,
        player: Player,
    ): Pair<ObjectData, ObjectData>
}
