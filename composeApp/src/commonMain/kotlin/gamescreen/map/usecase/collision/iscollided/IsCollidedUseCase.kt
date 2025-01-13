package gamescreen.map.usecase.collision.iscollided

import gamescreen.map.domain.collision.square.NormalSquare

interface IsCollidedUseCase {
    /**
     * 障害物と衝突しているかどうかをチェック
     */
    operator fun invoke(
        playerSquare: NormalSquare,
    ): Boolean
}
