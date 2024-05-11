package manager.map

import domain.map.BackgroundCell

class BackgroundCellManager(
    private val cellNum: Int,
    private val sideLength: Float,
) {
    private val backgroundCellArray: Array<Array<BackgroundCell>>

    init {
        backgroundCellArray = Array(cellNum) {
            Array(cellNum) {
                BackgroundCell()
            }
        }
    }
}
