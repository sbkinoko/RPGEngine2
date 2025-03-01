package gamescreen.map.usecase.resetposition

import gamescreen.map.data.MapData
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import kotlinx.coroutines.runBlocking

class ResetBackgroundPositionUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
) : ResetBackgroundPositionUseCase {

    override operator fun invoke(
        mapData: MapData,
        mapX: Int,
        mapY: Int,
    ) {
        runBlocking {
            // map情報を更新
            backgroundRepository.mapData = mapData

            val background =
                backgroundRepository.run {
                    List(allCellNum) { row ->
                        List(allCellNum) { col ->
                            val mapPoint = mapData.getMapPoint(
                                x = col - (cellNum - 1) / 2 + mapX,
                                y = row - (cellNum - 1) / 2 + mapY,
                            )
                            val cellType = mapData.getDataAt(mapPoint)

                            // 表示上の座標
                            val x = col * cellSize
                            val y = row * cellSize
                            val square = NormalSquare(
                                x = x,
                                y = y,
                                size = cellSize,
                                objectHeight = ObjectHeight.None,
                            )

                            BackgroundCell(
                                square = square,
                                mapPoint = mapPoint,
                                cellType = cellType
                            )
                        }
                    }
                }

            // 更新した情報を元に背景リセット
            backgroundRepository.setBackground(
                background = BackgroundData(
                    fieldData = background,
                ),
            )
        }
    }
}
