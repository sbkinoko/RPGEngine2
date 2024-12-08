package gamescreen.map.usecase

import gamescreen.map.ModuleMap
import gamescreen.map.domain.Velocity
import gamescreen.map.layout.PlayerMoveSquare
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class VelocityManagerUseCaseTest : KoinTest {

    private val velocityManageUseCase: VelocityManageUseCase by inject()
    private val moveToUseCase: PlayerMoveToUseCase by inject()

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
        runBlocking {
            moveToUseCase(
                x = CENTER,
                y = CENTER,
            )
            delay(100)

            check(
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
    }

    /**
     * 左にいて左に動くパターン
     */
    @Test
    fun checkMoveInLeftToLeft() {
        runBlocking {
            moveToUseCase(
                x = SMALL_BORDER,
                y = CENTER,
            )
            delay(100)
            check(
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
    }

    /**
     * 左にいて右に動くパターン
     */
    @Test
    fun checkMoveInLeftToRight() {
        runBlocking {
            moveToUseCase(
                x = SMALL_BORDER,
                y = CENTER,
            )
            delay(100)

            check(
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
    }

    /**
     * 右にいて左に動くパターン
     */
    @Test
    fun checkMoveInRightToLeft() {
        runBlocking {
            moveToUseCase(
                x = LARGE_BORDER,
                y = CENTER,
            )
            delay(100)
            check(
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
    }

    /**
     * 右にいて右に動くパターン
     */
    @Test
    fun checkMoveInRightToRight() {
        runBlocking {
            moveToUseCase(
                x = LARGE_BORDER,
                y = CENTER,
            )
            check(
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
    }

    /**
     * 上にいて上に動くパターン
     */
    @Test
    fun checkMoveInTopToTop() {
        runBlocking {
            moveToUseCase(
                x = CENTER,
                y = SMALL_BORDER,
            )
            delay(100)

            check(
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
    }

    /**
     * 上にいて下に動くパターン
     */
    @Test
    fun checkMoveInTopToBottom() {
        runBlocking {
            moveToUseCase(
                x = CENTER,
                y = SMALL_BORDER,
            )
            delay(100)

            check(
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
    }

    /**
     * 下にいて上に動くパターン
     */
    @Test
    fun checkMoveInBottomToTop() {
        runBlocking {
            moveToUseCase(
                x = CENTER,
                y = LARGE_BORDER,
            )
            delay(100)
            check(
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
    }

    /**
     * 下にいて下に動くパターン
     */
    @Test
    fun checkMoveInBottomToBottom() {
        runBlocking {
            moveToUseCase(
                x = CENTER,
                y = LARGE_BORDER,
            )
            delay(100)
            check(
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
    }

    private fun check(
        velocity: Velocity,
        vpx: Float,
        vpy: Float,
        vbx: Float,
        vby: Float,
    ) {
        velocityManageUseCase.manageVelocity(
            tentativePlayerVelocity = velocity,
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
