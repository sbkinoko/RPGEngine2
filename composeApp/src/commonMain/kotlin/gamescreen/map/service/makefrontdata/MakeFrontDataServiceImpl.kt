package gamescreen.map.service.makefrontdata

import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.background.FrontObjectData

class MakeFrontDataServiceImpl : MakeFrontDateService {
    // todo テストを作る
    override fun invoke(
        backgroundData: BackgroundData,
        player: Player,
    ): FrontObjectData {
        return FrontObjectData(
            backgroundData.fieldData.map {
                it.map { cell ->
                    val isAllFront = cell.collisionData.all {
                        player.square.objectHeight < it.objectHeight
                    }

                    if (isAllFront) {
                        cell
                    } else {
                        null
                    }
                }
            }
        )
    }
}
