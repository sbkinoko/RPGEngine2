package domain.map

import kotlin.test.Test
import kotlin.test.assertEquals

class VelocityMediatorTest {

    /**
     * 動かない
     */
    @Test
    fun checkNoMove() {
        check(
            player = Player(
                initX = CENTER,
                initY = CENTER,
                size = PLAYER_SIZE,
            ),
            vpx = 0f,
            vpy = 0f,
            vbx = 0f,
            vby = 0f,
        )
    }

    /**
     * 真ん中にいて適当に動く
     */
    @Test
    fun checkPlayerMove() {
        check(
            player = Player(
                initX = CENTER,
                initY = CENTER,
                size = PLAYER_SIZE,
            ).apply {
                velocity = Velocity(
                    dx = 3f,
                    dy = 4f,
                )
            },
            vpx = 3f,
            vpy = 4f,
            vbx = 0f,
            vby = 0f,
        )
    }

    /**
     * 左にいて左に動くパターン
     */
    @Test
    fun checkMoveInLeftToLeft() {
        check(
            player = Player(
                initX = 5f,
                initY = CENTER,
                size = PLAYER_SIZE,
            ).apply {
                velocity = Velocity(
                    dx = -3f,
                    dy = 0f,
                )
            },
            vpx = 0f,
            vpy = 0f,
            vbx = 3f,
            vby = 0f,
        )
    }

    /**
     * 左にいて右に動くパターン
     */
    @Test
    fun checkMoveInLeftToRight() {
        check(
            player = Player(
                initX = 5f,
                initY = CENTER,
                size = PLAYER_SIZE,
            ).apply {
                velocity = Velocity(
                    dx = 3f,
                    dy = 0f,
                )
            },
            vpx = 3f,
            vpy = 0f,
            vbx = 0f,
            vby = 0f,
        )
    }

    /**
     * 右にいて左に動くパターン
     */
    @Test
    fun checkMoveInRightToLeft() {
        check(
            player = Player(
                initX = 45f,
                initY = CENTER,
                size = PLAYER_SIZE,
            ).apply {
                velocity = Velocity(
                    dx = -3f,
                    dy = 0f,
                )
            },
            vpx = -3f,
            vpy = 0f,
            vbx = 0f,
            vby = 0f,
        )
    }

    /**
     * 右にいて右に動くパターン
     */
    @Test
    fun checkMoveInRightToRight() {
        check(
            player = Player(
                initX = 45f,
                initY = CENTER,
                size = PLAYER_SIZE,
            ).apply {
                velocity = Velocity(
                    dx = 3f,
                    dy = 0f,
                )
            },
            vpx = 0f,
            vpy = 0f,
            vbx = -3f,
            vby = 0f,
        )
    }

    /**
     * 上にいて上に動くパターン
     */
    @Test
    fun checkMoveInTopToTop() {
        check(
            player = Player(
                initX = CENTER,
                initY = 5f,
                size = PLAYER_SIZE,
            ).apply {
                velocity = Velocity(
                    dx = 0f,
                    dy = -4f,
                )
            },
            vpx = 0f,
            vpy = 0f,
            vbx = 0f,
            vby = 4f,
        )
    }

    /**
     * 上にいて下に動くパターン
     */
    @Test
    fun checkMoveInTopToBottom() {
        check(
            player = Player(
                initX = CENTER,
                initY = 5f,
                size = PLAYER_SIZE,
            ).apply {
                velocity = Velocity(
                    dx = 0f,
                    dy = 4f,
                )
            },
            vpx = 0f,
            vpy = 4f,
            vbx = 0f,
            vby = 0f,
        )
    }

    /**
     * 下にいて上に動くパターン
     */
    @Test
    fun checkMoveInBottomToTop() {
        check(
            player = Player(
                initX = CENTER,
                initY = 45f,
                size = PLAYER_SIZE,
            ).apply {
                velocity = Velocity(
                    dx = 0f,
                    dy = -4f,
                )
            },
            vpx = 0f,
            vpy = -4f,
            vbx = 0f,
            vby = 0f,
        )
    }

    /**
     * 下にいて下に動くパターン
     */
    @Test
    fun checkMoveInBottomToBottom() {
        check(
            player = Player(
                initX = CENTER,
                initY = 45f,
                size = PLAYER_SIZE,
            ).apply {
                velocity = Velocity(
                    dx = 0f,
                    dy = 4f,
                )
            },
            vpx = 0f,
            vpy = 0f,
            vbx = 0f,
            vby = -4f,
        )
    }


    private fun check(
        player: Player,
        vpx: Float,
        vpy: Float,
        vbx: Float,
        vby: Float,
    ) {
        VelocityMediator.mediateVelocity(
            player = player,
            playerMoveArea = Square(
                x = 10f,
                y = 10f,
                size = 30f
            ),
        ).apply {
            first.apply {
                assertEquals(
                    expected = vpx,
                    actual = x,
                )

                assertEquals(
                    expected = vpy,
                    actual = y,
                )
            }

            second.apply {
                assertEquals(
                    expected = vbx,
                    actual = x,
                )

                assertEquals(
                    expected = vby,
                    actual = y,
                )
            }
        }
    }

    companion object {
        private const val CENTER = 25f
        private const val PLAYER_SIZE = 5f
    }
}
