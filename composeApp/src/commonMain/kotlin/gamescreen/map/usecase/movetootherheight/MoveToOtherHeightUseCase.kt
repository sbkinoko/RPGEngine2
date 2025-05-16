package gamescreen.map.usecase.movetootherheight

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.npc.NPCData

interface MoveToOtherHeightUseCase {

    /**
     * @param targetHeight 移動したい高さ
     */
    suspend operator fun invoke(
        targetHeight: ObjectHeight,
        backgroundData: BackgroundData,
        player: Player,
        npcData: NPCData,
        update: (Player) -> Unit = {},
    )
}
