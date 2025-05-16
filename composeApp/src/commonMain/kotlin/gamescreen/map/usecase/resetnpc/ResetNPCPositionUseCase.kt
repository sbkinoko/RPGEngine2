package gamescreen.map.usecase.resetnpc

import gamescreen.map.data.MapData
import gamescreen.map.domain.npc.NPCData

interface ResetNPCPositionUseCase {

    /**
     * マップの中心を指定して、NPCデータをロードする
     */
    operator fun invoke(
        mapData: MapData,
        mapX: Int,
        mapY: Int,
    ): NPCData
}
