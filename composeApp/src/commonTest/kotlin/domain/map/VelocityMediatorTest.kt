package domain.map

import layout.map.PlayerMoveSquare
import viewmodel.MapViewModel
import kotlin.test.Test
import kotlin.test.assertEquals

class VelocityMediatorTest {

    /**
     * 動かない
     */
    @Test
    fun checkNoMove() {
        check(
            velocity = Velocity(),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
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
            velocity = Velocity(
                dx = 3f,
                dy = 4f,
            ),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
            ).apply {
                moveTo(
                    point = Point(
                        x = CENTER,
                        y = CENTER,
                    )
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
            velocity = Velocity(
                dx = -3f,
                dy = 0f,
            ),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
            ).apply {
                moveTo(
                    point = Point(
                        x = SMALL_BORDER,
                        y = CENTER,
                    )
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
            velocity = Velocity(
                dx = 3f,
                dy = 0f,
            ),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
            ).apply {
                moveTo(
                    point = Point(
                        x = SMALL_BORDER,
                        y = CENTER,
                    )
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
            velocity = Velocity(
                dx = -3f,
                dy = 0f,
            ),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
            ).apply {
                moveTo(
                    Point(
                        x = LARGE_BORDER,
                        y = CENTER,
                    )
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
            velocity = Velocity(
                dx = 3f,
                dy = 0f,
            ),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
            ).apply {
                moveTo(
                    point = Point(
                        x = LARGE_BORDER,
                        y = CENTER,
                    )
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
            velocity = Velocity(
                dx = 0f,
                dy = -4f,
            ),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
            ).apply {
                moveTo(
                    point = Point(
                        x = CENTER,
                        y = SMALL_BORDER,
                    )
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
            velocity = Velocity(
                dx = 0f,
                dy = 4f,
            ),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
            ).apply {
                moveTo(
                    point = Point(
                        x = CENTER,
                        y = SMALL_BORDER,
                    )
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
            velocity = Velocity(
                dx = 0f,
                dy = -4f,
            ),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
            ).apply {
                moveTo(
                    point = Point(
                        x = CENTER,
                        y = LARGE_BORDER,
                    )
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
            velocity = Velocity(
                dx = 0f,
                dy = 4f,
            ),
            player = Player(
                size = MapViewModel.VIRTUAL_PLAYER_SIZE,
            ).apply {
                moveTo(
                    point = Point(
                        x = CENTER,
                        y = LARGE_BORDER,
                    )
                )
            },
            vpx = 0f,
            vpy = 0f,
            vbx = 0f,
            vby = -4f,
        )
    }


    private fun check(
        velocity: Velocity,
        player: Player,
        vpx: Float,
        vpy: Float,
        vbx: Float,
        vby: Float,
    ) {
        VelocityManager.manageVelocity(
            tentativePlayerVelocity = velocity,
            player = player.square,
            playerMoveArea = PlayerMoveSquare(
                screenSize = MapViewModel.VIRTUAL_SCREEN_SIZE,
                borderRate = MapViewModel.MOVE_BORDER,
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
        private const val CENTER = MapViewModel.VIRTUAL_SCREEN_SIZE / 2f
        private const val LARGE_BORDER =
            MapViewModel.VIRTUAL_SCREEN_SIZE * (1f - MapViewModel.MOVE_BORDER) + MapViewModel.VIRTUAL_PLAYER_SIZE
        private const val SMALL_BORDER =
            MapViewModel.VIRTUAL_SCREEN_SIZE * MapViewModel.MOVE_BORDER - MapViewModel.VIRTUAL_PLAYER_SIZE
    }
}
