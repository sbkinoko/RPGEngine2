package gamescreen.map.service.makefrontdata

import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.background.ObjectData

class MakeFrontDataServiceImpl : MakeFrontDateService {
    // todo テストを作る
    override fun invoke(
        backgroundData: BackgroundData,
        player: Player,
    ): Pair<ObjectData, ObjectData> {
        val frontData: MutableList<List<BackgroundCell?>> = mutableListOf()
        val backData: MutableList<List<BackgroundCell?>> = mutableListOf()

        backgroundData.fieldData.mapIndexed { row, it ->
            val frontRowData: MutableList<BackgroundCell?> = mutableListOf()
            val backRowData: MutableList<BackgroundCell?> = mutableListOf()

            it.mapIndexed { col, cell ->
                val isAllFront = cell.collisionData.all {
                    player.square.objectHeight < it.objectHeight
                }

                if (isAllFront) {
                    frontRowData.add(cell)
                    backRowData.add(null)
                } else {
                    frontRowData.add(null)
                    backRowData.add(cell)
                }
            }
            frontData.add(frontRowData.toList())
            backData.add(backRowData.toList())
        }

        return ObjectData(frontData) to ObjectData(backData)
    }
}
