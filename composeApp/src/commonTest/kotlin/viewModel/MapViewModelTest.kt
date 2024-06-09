package viewModel

import domain.map.Velocity
import viewmodel.MapViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MapViewModelTest {
    private lateinit var mapViewModel: MapViewModel

    @BeforeTest
    fun beforeTest() {
        mapViewModel = MapViewModel(PLAYER_SIZE)
        mapViewModel.initBackgroundCellManager(SCREEN_SIZE)
    }

    @Test
    fun updateVelocity() {
        run {
            val x = CENTER + 2f
            val y = CENTER + 1f
            mapViewModel.setTapPoint(
                x = x,
                y = y,
            )

            mapViewModel.updatePosition()

            mapViewModel.playerPosition.value.apply {
                assertEquals(
                    x - PLAYER_SIZE / 2,
                    this.x,
                )
                assertEquals(
                    y - PLAYER_SIZE / 2,
                    this.y,
                )
            }
        }
    }


    /**
     * 一回のタップで長く移動
     */
    @Test
    fun updatePosition2Times() {
        val x = SCREEN_SIZE.toFloat()
        val y = CENTER
        mapViewModel.setTapPoint(
            x = x,
            y = y,
        )

        mapViewModel.updatePosition()
        mapViewModel.playerPosition.value.apply {
            assertEquals(
                CENTER + Velocity.MAX - PLAYER_SIZE / 2,
                this.x,
            )
            assertEquals(
                y - PLAYER_SIZE / 2,
                this.y,
            )
        }

        mapViewModel.updatePosition()
        mapViewModel.playerPosition.value.apply {
            assertEquals(
                CENTER + Velocity.MAX * 2 - PLAYER_SIZE / 2,
                this.x,
            )
            assertEquals(
                y - PLAYER_SIZE / 2,
                this.y,
            )
        }

        mapViewModel.resetTapPoint()
        mapViewModel.updatePosition()
        mapViewModel.playerPosition.value.apply {
            assertEquals(
                CENTER + Velocity.MAX * 2 - PLAYER_SIZE / 2,
                this.x,
            )
            assertEquals(
                y - PLAYER_SIZE / 2,
                this.y,
            )
        }
    }

    @Test
    fun resetTapPoint() {
        val x = SCREEN_SIZE + 2f
        val y = SCREEN_SIZE + 1f
        mapViewModel.setTapPoint(
            x = x,
            y = y,
        )
        mapViewModel.resetTapPoint()

        mapViewModel.updatePosition()

        mapViewModel.playerPosition.value.apply {
            assertEquals(
                SCREEN_SIZE / 2 - PLAYER_SIZE / 2,
                this.x,
            )
            assertEquals(
                SCREEN_SIZE / 2 - PLAYER_SIZE / 2,
                this.y,
            )
        }
    }

    companion object {
        private const val PLAYER_SIZE = 5f
        private const val SCREEN_SIZE = 100
        private const val CENTER = SCREEN_SIZE / 2f
    }
}
