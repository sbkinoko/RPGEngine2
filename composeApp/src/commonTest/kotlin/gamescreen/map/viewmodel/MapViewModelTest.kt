package gamescreen.map.viewmodel

import gamescreen.map.MapModule
import gamescreen.map.domain.Velocity
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
            modules(MapModule)
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
            collectJob.cancel()
            assertEquals(
                expected = 1,
                actual = count,
            )
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
            val collectJob = launch {
                mapViewModel.playerSquare.collect {
                    when (count) {
                        0 -> {
                            assertEquals(
                                CENTER + Velocity.MAX - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                                it.x,
                            )
                            assertEquals(
                                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                                it.y,
                            )
                        }

                        1 -> {
                            assertEquals(
                                CENTER + Velocity.MAX * 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                                it.x,
                            )
                            assertEquals(
                                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                                it.y,
                            )
                        }
                    }
                    count++
                }
            }

            mapViewModel.setTapPoint(
                x = x,
                y = y,
            )

            mapViewModel.updatePosition()
            delay(100)

            mapViewModel.updatePosition()
            delay(100)

            // 値が更新されないことを確認
            mapViewModel.resetTapPoint()
            mapViewModel.updatePosition()
            delay(100)

            collectJob.cancel()
            // updateは3回しているが、値の更新は2回なのでcollectは2回しか呼ばれない
            assertEquals(
                expected = 2,
                actual = count,
            )
        }
    }

    @Test
    fun resetTapPoint() {
        val x = MapViewModel.VIRTUAL_SCREEN_SIZE + 2f
        val y = MapViewModel.VIRTUAL_SCREEN_SIZE + 1f
        var count = 0

        runBlocking {
            val collectJob = launch {
                mapViewModel.playerSquare.collect {
                    assertEquals(
                        MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                        it.x,
                    )
                    assertEquals(
                        MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                        it.y,
                    )
                    count++
                }
            }
            mapViewModel.setTapPoint(
                x = x,
                y = y,
            )
            mapViewModel.resetTapPoint()

            mapViewModel.updatePosition()
            delay(100)
            collectJob.cancel()

            assertEquals(
                expected = 1,
                actual = count
            )
        }
    }

    companion object {
        private const val CENTER = MapViewModel.VIRTUAL_SCREEN_SIZE / 2f
    }
}
