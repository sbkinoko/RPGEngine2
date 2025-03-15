package gamescreen.map.usecase.collision.iscollided

import gamescreen.map.domain.collision.square.Rectangle

interface IsCollidedUseCase {
    /**
     * 障害物と衝突しているかどうかをチェック
     */
    operator fun invoke(
        playerSquare: Rectangle,
    ): Boolean
}
