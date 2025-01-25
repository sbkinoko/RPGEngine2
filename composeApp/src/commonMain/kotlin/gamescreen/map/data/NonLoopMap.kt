package gamescreen.map.data

import core.domain.mapcell.CellType
import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.npc.NPC
import gamescreen.map.domain.npc.NPCType
import gamescreen.map.viewmodel.MapViewModel
import values.event.EventType
import values.event.ShopId
import values.event.TalkEvent

class NonLoopMap : MapData() {
    override val isLoop = false
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10

    override val npcList: List<NPC>
        get() = listOf(
            NPC(
                npcType = NPCType.MARCHANT,
                mapPoint = MapPoint(3, 3),
                eventType = EventType.Shop(
                    shopId = ShopId.Type1,
                ),
                size = MapViewModel.CELL_SIZE * 0.5f,
            ),
            NPC(
                npcType = NPCType.GIRL,
                mapPoint = MapPoint(8, 8),
                eventType = TalkEvent.Talk1,
                size = MapViewModel.CELL_SIZE,
            ),
            NPC(
                npcType = NPCType.MARCHANT,
                mapPoint = MapPoint(4, 3),
                eventType = EventType.Shop(
                    shopId = ShopId.Type2,
                ),
                size = MapViewModel.CELL_SIZE,
            ),
            NPC(
                npcType = NPCType.ENEMY,
                mapPoint = MapPoint(3, 1),
                eventType = TalkEvent.Talk2,
                size = MapViewModel.CELL_SIZE,
            ),
        )

    override val field: Array<Array<CellType>>
        get() = Array(height) { y ->
            Array(width) { x ->
                if (x == 0 && y == 2) {
                    CellType.Town1O
                } else if (x == 0 || x == 9 ||
                    y == 0 || y == 9
                ) {
                    CellType.Water
                } else {
                    CellType.Glass
                }
            }
        }
}
