package gamescreen.map.data

import core.domain.mapcell.CellType
import gamescreen.map.domain.npc.NPC

class LoopTestMap : MapData() {
    override val isLoop = true
    override val npcList: List<NPC>
        get() = emptyList()

    override val width: Int
        get() = 4
    override val height: Int
        get() = 4
    override val field: Array<Array<CellType>>
        get() = Array(height) { y ->
            Array(width) { x ->
                CellType.TextCell(
                    id = x + width * y,
                )
            }
        }
}
