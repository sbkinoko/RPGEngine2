package map.usecase

import kotlinx.coroutines.runBlocking
import map.domain.BackgroundCell
import map.domain.MapData
import map.repository.backgroundcell.BackgroundRepository

class ResetBackgroundPositionUseCase(
    private val repository: BackgroundRepository,
) {
    operator fun invoke(
        allCellNum: Int,
        cellNum: Int,
        cellSize: Float,
        mapData: MapData,
        mapX: Int,
        mapY: Int,
    ) {
        runBlocking {
            // map情報を更新
            repository.mapData = mapData

            // 更新した情報を元に背景リセット
            repository.setBackground(
                List(allCellNum) { row ->
                    List(allCellNum) { col ->
                        BackgroundCell(
                            x = col * cellSize,
                            y = row * cellSize,
                            cellSize = cellSize,
                        ).apply {
                            repository.mapData.apply {
                                mapPoint = getMapPoint(
                                    x = col - (cellNum - 1) / 2 + mapX,
                                    y = row - (cellNum - 1) / 2 + mapY,
                                )
                                imgID = getDataAt(mapPoint)
                            }
                        }
                    }
                }
            )
        }
    }
}
