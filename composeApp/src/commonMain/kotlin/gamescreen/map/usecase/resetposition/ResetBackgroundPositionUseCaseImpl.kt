package gamescreen.map.usecase.resetposition

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.MapData
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.collision.CollisionRepository
import kotlinx.coroutines.runBlocking

class ResetBackgroundPositionUseCaseImpl(
    private val repository: BackgroundRepository,
    private val collisionRepository: CollisionRepository,
) : ResetBackgroundPositionUseCase {

    override operator fun invoke(
        mapData: MapData,
        mapX: Int,
        mapY: Int,
    ) {
        runBlocking {
            // map情報を更新
            repository.mapData = mapData

            val background: List<List<BackgroundCell>> =
                repository.run {
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

                            BackgroundCell(
                                x = x,
                                y = y,
                                cellSize = cellSize,
                                mapPoint = mapPoint,
                                cellType = cellType
                            ).run {
                                // fixme collisionListがなくなったら消す
                                this.copy(
                                    collisionList = collisionRepository
                                        .collisionData(
                                            square = square,
                                            cellType = cellType,
                                        )
                                )
                            }
                        }
                    }
                }

            // 更新した情報を元に背景リセット
            repository.setBackground(
                background = background,
            )
        }
    }
}
