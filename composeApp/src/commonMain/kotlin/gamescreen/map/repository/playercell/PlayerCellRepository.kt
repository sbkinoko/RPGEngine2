package gamescreen.map.repository.playercell

import gamescreen.map.domain.BackgroundCell
import kotlinx.coroutines.flow.StateFlow

interface PlayerCellRepository {
    val playerIncludeCellFlow: StateFlow<BackgroundCell?>
    var playerIncludeCell: BackgroundCell?
    var playerCenterCell: BackgroundCell

    val eventCell: BackgroundCell?
}
