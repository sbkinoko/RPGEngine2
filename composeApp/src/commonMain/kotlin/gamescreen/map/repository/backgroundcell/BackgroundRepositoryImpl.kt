package gamescreen.map.repository.backgroundcell

import core.domain.mapcell.CellType
import gamescreen.map.data.LoopMap
import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.MapData
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class BackgroundRepositoryImpl : BackgroundRepository {
    override val backgroundFlow: MutableSharedFlow<List<List<BackgroundCell>>> =
        MutableSharedFlow(replay = 1)
    override var background: List<List<BackgroundCell>> = BackgroundRepository.initialBackground

    override var mapData: MapData = LoopMap()

    override var cellNum: Int = 3

    override val allCellNum: Int
        get() = cellNum + 1

    override var screenSize: Int = MapViewModel.VIRTUAL_SCREEN_SIZE
    override val cellSize: Float
        get() = screenSize / cellNum.toFloat()

    override fun getBackgroundAt(x: Int, y: Int): BackgroundCell {
        return background[y][x]
    }

    override fun getBackgroundAround(x: Int, y: Int): Array<Array<CellType>> {
        return arrayOf(
            arrayOf(
                getIdAt(x = x - 1, y = y - 1),
                getIdAt(x = x, y = y - 1),
                getIdAt(x = x + 1, y = y - 1),
            ),
            arrayOf(
                getIdAt(x = x - 1, y = y),
                getIdAt(x = x, y = y),
                getIdAt(x = x + 1, y = y),
            ),
            arrayOf(
                getIdAt(x = x - 1, y = y + 1),
                getIdAt(x = x, y = y + 1),
                getIdAt(x = x + 1, y = y + 1),
            )
        )
    }

    override suspend fun setBackground(background: List<List<BackgroundCell>>) {
        this.background = background
        backgroundFlow.emit(background)
    }

    override suspend fun reload() {
        backgroundFlow.emit(background)
    }

    private fun getIdAt(x: Int, y: Int): CellType {
        return if (mapData.isLoop) {
            mapData.getDataAt(
                x = correctX(x),
                y = correctY(y)
            )
        } else {
            if (isOutX(x) || isOutY(y)) {
                CellType.Null
            } else {
                mapData.getDataAt(x, y)
            }
        }
    }

    private fun isOutX(x: Int): Boolean {
        return (x < 0 || x >= mapData.width)
    }

    private fun isOutY(y: Int): Boolean {
        return (y < 0 || y >= mapData.height)
    }

    private fun correctX(x: Int): Int {
        if (x < 0) {
            return mapData.width - 1
        }

        if (x >= mapData.width) {
            return 0
        }

        return x
    }

    private fun correctY(y: Int): Int {
        if (y < 0) {
            return mapData.height - 1
        }

        if (y >= mapData.height) {
            return 0
        }

        return y
    }
}
