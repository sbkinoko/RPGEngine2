package gamescreen.map.data

import core.domain.mapcell.CellType
import gamescreen.map.domain.npc.NPC

class ForestMap : MapData() {
    override val isLoop = false
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10

    override val outerCell: CellType
        get() = CellType.ForestExit

    override val npcList: List<NPC>
        get() = listOf(

        )

    override val field: Array<Array<CellType>>
        get() = arrayOf(
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.ForestWood,
                CellType.ForestWood,
                CellType.ForestWood,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.ForestWood,
                CellType.ForestWood,
                CellType.ForestWood,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.ForestWood,
                CellType.ForestWood,
                CellType.ForestWood,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
            arrayOf(
                CellType.ForestExit,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
                CellType.Forest,
            ),
        )
}
