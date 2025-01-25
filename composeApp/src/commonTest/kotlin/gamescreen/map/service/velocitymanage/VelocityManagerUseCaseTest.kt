package gamescreen.map.service.velocitymanage

import gamescreen.map.ModuleMap
import gamescreen.map.domain.Player
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.PlayerMoveSquare
import gamescreen.map.viewmodel.MapViewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class VelocityManagerUseCaseTest : KoinTest {
    private val velocityManageUseCase: VelocityManageService by inject()

    private val playerMoveSquare = PlayerMoveSquare(
        screenSize = MapViewModel.VIRTUAL_SCREEN_SIZE,
        borderRate = MapViewModel.MOVE_BORDER,
    )

    private val player: Player = Player(size = MapViewModel.VIRTUAL_PLAYER_SIZE)

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * 動かない
     */
    @Test
    fun checkNoMove() {
        check(
            player = player,
            velocity = Velocity(),
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
            player = player.moveTo(
                x = CENTER,
                y = CENTER,
            ),
            velocity = Velocity(
                x = 3f,
                y = 4f,
            ),
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
            player = player.moveTo(
                x = SMALL_BORDER,
                y = CENTER,

                ),
            velocity = Velocity(
                x = -3f,
                y = 0f,
            ),
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
            player = player.moveTo(
                x = SMALL_BORDER,
                y = CENTER,
            ),
            velocity = Velocity(
                x = 3f,
                y = 0f,
            ),
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
            player = player.moveTo(
                x = LARGE_BORDER,
                y = CENTER,
            ),
            velocity = Velocity(
                x = -3f,
                y = 0f,
            ),
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
            player = player.moveTo(
                x = LARGE_BORDER,
                y = CENTER,
            ),
            velocity = Velocity(
                x = 3f,
                y = 0f,
            ),
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
            player = player.moveTo(
                x = CENTER,
                y = SMALL_BORDER,

                ),
            velocity = Velocity(
                x = 0f,
                y = -4f,
            ),
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
            player = player.moveTo(
                x = CENTER,
                y = SMALL_BORDER,
            ),
            velocity = Velocity(
                x = 0f,
                y = 4f,
            ),
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
            player = player.moveTo(
                x = CENTER,
                y = LARGE_BORDER,
            ),
            velocity = Velocity(
                x = 0f,
                y = -4f,
            ),

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
            player = player.moveTo(
                x = CENTER,
                y = LARGE_BORDER,

                ),
            velocity = Velocity(
                x = 0f,
                y = 4f,
            ),
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
        velocityManageUseCase.invoke(
            tentativePlayerVelocity = velocity,
            playerMoveArea = playerMoveSquare.square,
            playerSquare = player.square,
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
