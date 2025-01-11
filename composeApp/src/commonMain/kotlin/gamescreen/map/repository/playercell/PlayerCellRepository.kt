package gamescreen.map.repository.playercell

import gamescreen.map.domain.BackgroundCell
import kotlinx.coroutines.flow.StateFlow

interface PlayerCellRepository {
    var playerIncludeCell: BackgroundCell?

    val eventCell: BackgroundCell?

    val playerIncludeCellFlow: StateFlow<BackgroundCell?>
}
