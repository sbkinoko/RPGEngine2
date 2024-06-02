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
        // 主人公の位置は(15,15)
        mapViewModel.initBackgroundCellManager(30)
    }

    @Test
    fun updateVelocity() {
        val x = 17f
        val y = 16f
        mapViewModel.setTapPoint(
            x = x,
            y = x,
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

    companion object{
        private const val PLAYER_SIZE = 5f
    }
}
