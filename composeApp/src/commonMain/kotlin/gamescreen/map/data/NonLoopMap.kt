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
            NPC(
                npcType = NPCType.TEMPLATE,
                mapPoint = MapPoint(5, 1),
                eventType = TalkEvent.Talk2,
                size = MapViewModel.CELL_SIZE * 0.8f,
            ),

            NPC(
                npcType = NPCType.BOY,
                mapPoint = MapPoint(5, 3),
                eventType = TalkEvent.Talk2,
                size = MapViewModel.CELL_SIZE * 0.8f,
            ),
        )

    override val field: Array<Array<CellType>>
        get() = arrayOf(
            arrayOf(
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
            ),
            arrayOf(
                CellType.Water,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
            ),
            arrayOf(
                CellType.Town1O,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
            ),
            arrayOf(
                CellType.Water,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
            ),
            arrayOf(
                CellType.Water,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
            ),
            arrayOf(
                CellType.Water,
                CellType.Glass,
                CellType.BridgeLeftTop,
                CellType.BridgeCenterTop,
                CellType.BridgeCenterTop,
                CellType.BridgeRightTop,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
            ),
            arrayOf(
                CellType.Water,
                CellType.Glass,
                CellType.BridgeLeftUnder,
                CellType.BridgeCenterBottom,
                CellType.BridgeCenterBottom,
                CellType.BridgeRightUnder,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
            ),
            arrayOf(
                CellType.Water,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
            ),
            arrayOf(
                CellType.Water,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
            ),
            arrayOf(
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
            ),
        )
}
