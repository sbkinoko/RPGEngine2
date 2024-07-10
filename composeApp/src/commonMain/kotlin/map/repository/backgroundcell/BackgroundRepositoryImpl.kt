package map.repository.backgroundcell

import kotlinx.coroutines.flow.MutableSharedFlow
import map.data.LoopMap
import map.domain.BackgroundCell
import map.domain.MapData
import map.viewmodel.MapViewModel

class BackgroundRepositoryImpl : BackgroundRepository {
    override val backgroundFlow: MutableSharedFlow<List<List<BackgroundCell>>> =
        MutableSharedFlow(replay = 1)
    override var background: List<List<BackgroundCell>> = BackgroundRepository.initialBackground

    override var mapData: MapData = LoopMap()

    override var cellNum: Int = 3

    override val allCellNum: Int
        get() = cellNum + 1

    override var screenSeize: Int = MapViewModel.VIRTUAL_SCREEN_SIZE
    override val cellSize: Float
        get() = screenSeize / cellNum.toFloat()

    override fun getBackgroundAt(x: Int, y: Int): BackgroundCell {
        return background[y][x]
    }

    override suspend fun setBackground(background: List<List<BackgroundCell>>) {
        this.background = background
        backgroundFlow.emit(background)
    }

    override suspend fun reload() {
        backgroundFlow.emit(background)
    }
}
