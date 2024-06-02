package viewModel

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
        val x = 17f
        val y = 16f
        mapViewModel.setTapPoint(
            x = x,
            y = y,
        )

        mapViewModel.updatePosition()

        mapViewModel.playerPosition.value.apply {
            assertEquals(
                x- PLAYER_SIZE/2,
                this.x,
            )
            assertEquals(
                y - PLAYER_SIZE/2,
                this.y,
            )
        }
    }

    @Test
    fun resetTapPoint() {
        val x = 17f
        val y = 16f
        mapViewModel.setTapPoint(
            x = x,
            y = y,
        )
        mapViewModel.resetTapPoint()

        mapViewModel.updatePosition()

        mapViewModel.playerPosition.value.apply {
            assertEquals(
                SCREEN_SIZE/2 - PLAYER_SIZE/2,
                this.x,
            )
            assertEquals(
                SCREEN_SIZE/2 - PLAYER_SIZE/2,
                this.y,
            )
        }
    }

    companion object{
        private const val PLAYER_SIZE = 5f
        private const val SCREEN_SIZE = 30
    }
}
