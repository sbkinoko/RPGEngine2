package gamescreen.map.repository.playercell

import gamescreen.map.domain.BackgroundCell
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerCellRepositoryImpl : PlayerCellRepository {
    private val mutablePlayerIncludeCellFlow =
        MutableStateFlow<BackgroundCell?>(null)

    override val playerIncludeCellFlow: StateFlow<BackgroundCell?>
        get() = mutablePlayerIncludeCellFlow.asStateFlow()

    private var prePlayerIncludeCell: BackgroundCell? = null

    override var playerIncludeCell: BackgroundCell? = null
        set(value) {
            prePlayerIncludeCell = field
            mutablePlayerIncludeCellFlow.value = value
            field = value
        }

    override val eventCell: BackgroundCell?
        get() {
            val preMapPoint = prePlayerIncludeCell?.mapPoint
            val mapPoint = playerIncludeCell?.mapPoint

            //同一のマスないなら何も起こさない
            if (preMapPoint == mapPoint) {
                return null
            }

            //違うなら始めて全身が入ったのでイベント用に情報返却
            return playerIncludeCell
        }
}
