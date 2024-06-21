package viewModel

import map.domain.Velocity
import map.viewmodel.MapViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MapViewModelTest {
    private lateinit var mapViewModel: MapViewModel

    @BeforeTest
    fun beforeTest() {
        mapViewModel = MapViewModel()
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
                    x - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                    this.x,
                )
                assertEquals(
                    y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
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
        val x = MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat()
        val y = CENTER
        mapViewModel.setTapPoint(
            x = x,
            y = y,
        )

        mapViewModel.updatePosition()
        mapViewModel.playerPosition.value.apply {
            assertEquals(
                CENTER + Velocity.MAX - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                this.x,
            )
            assertEquals(
                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                this.y,
            )
        }

        mapViewModel.updatePosition()
        mapViewModel.playerPosition.value.apply {
            assertEquals(
                CENTER + Velocity.MAX * 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                this.x,
            )
            assertEquals(
                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                this.y,
            )
        }

        mapViewModel.resetTapPoint()
        mapViewModel.updatePosition()
        mapViewModel.playerPosition.value.apply {
            assertEquals(
                CENTER + Velocity.MAX * 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                this.x,
            )
            assertEquals(
                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                this.y,
            )
        }
    }

    @Test
    fun resetTapPoint() {
        val x = MapViewModel.VIRTUAL_SCREEN_SIZE + 2f
        val y = MapViewModel.VIRTUAL_SCREEN_SIZE + 1f
        mapViewModel.setTapPoint(
            x = x,
            y = y,
        )
        mapViewModel.resetTapPoint()

        mapViewModel.updatePosition()

        mapViewModel.playerPosition.value.apply {
            assertEquals(
                MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                this.x,
            )
            assertEquals(
                MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                this.y,
            )
        }
    }

    companion object {
        private const val CENTER = MapViewModel.VIRTUAL_SCREEN_SIZE / 2f
    }
}
