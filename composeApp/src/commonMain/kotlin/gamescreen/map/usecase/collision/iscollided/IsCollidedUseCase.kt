package gamescreen.map.usecase.collision.iscollided

import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.domain.npc.NPCData

interface IsCollidedUseCase {
    /**
     * 障害物と衝突しているかどうかをチェック
     */
    operator fun invoke(
        playerSquare: Rectangle,
        backgroundData: BackgroundData,
        npcData: NPCData,
    ): Boolean
}
