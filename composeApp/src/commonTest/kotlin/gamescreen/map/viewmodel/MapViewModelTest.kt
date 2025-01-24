package gamescreen.map.viewmodel

import gamescreen.map.ModuleMap
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.Square
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MapViewModelTest {
    private lateinit var mapViewModel: MapViewModel

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(ModuleMap)
        }
        mapViewModel = MapViewModel()
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun updateVelocity() {
        runBlocking {
            val x = CENTER + 2f
            val y = CENTER + 1f
            var count = 0
            val collectJob = launch {
                mapViewModel.playerSquare.collect {
                    count++
                    assertEquals(
                        x - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                        it.x,
                    )
                    assertEquals(
                        y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                        it.y,
                    )
                }
            }

            mapViewModel.setTapPoint(
                x = x,
                y = y,
            )

            mapViewModel.updatePosition()

            delay(100)

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    /**
     * 一回のタップで長く移動
     */
    @Test
    fun updatePosition2Times() {
        val x = MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat()
        val y = CENTER
        var count = 0
        runBlocking {
            lateinit var result: Square
            val collectJob = launch {
                mapViewModel.playerSquare.collect {
                    result = it
                    count++
                }
            }

            mapViewModel.setTapPoint(
                x = x,
                y = y,
            )

            mapViewModel.updatePosition()
            delay(100)

            assertEquals(
                CENTER + Velocity.MAX - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.x,
            )
            assertEquals(
                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.y,
            )

            mapViewModel.updatePosition()
            delay(100)

            assertEquals(
                CENTER + Velocity.MAX * 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.x,
            )
            assertEquals(
                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.y,
            )

            // 値が更新されないことを確認
            mapViewModel.resetTapPoint()
            mapViewModel.updatePosition()
            delay(100)

            assertEquals(
                CENTER + Velocity.MAX * 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.x,
            )
            assertEquals(
                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.y,
            )

            // updateは3回しているが、値の更新は2回なのでcollectは2回しか呼ばれない
            assertEquals(
                expected = 2,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun resetTapPoint() {
        val x = MapViewModel.VIRTUAL_SCREEN_SIZE + 2f
        val y = MapViewModel.VIRTUAL_SCREEN_SIZE + 1f
        var count = 0

        runBlocking {
            lateinit var result: Square
            val collectJob = launch {
                mapViewModel.playerSquare.collect {
                    result = it
                    count++
                }
            }

            delay(50)

            mapViewModel.setTapPoint(
                x = x,
                y = y,
            )
            mapViewModel.resetTapPoint()

            mapViewModel.updatePosition()
            delay(100)

            assertEquals(
                MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.x,
            )
            assertEquals(
                MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.y,
            )

            assertEquals(
                expected = 2,
                actual = count
            )

            collectJob.cancel()
        }
    }

    companion object {
        private const val CENTER = MapViewModel.VIRTUAL_SCREEN_SIZE / 2f
    }
}
