package gamescreen.map.repository.playercell

import gamescreen.map.domain.BackgroundCell

class PlayerCellRepositoryImpl : PlayerCellRepository {
    private var prePlayerIncludeCell: BackgroundCell? = null
    private var _playerIncludeCell: BackgroundCell? = null

    override var playerIncludeCell: BackgroundCell?
        get() {
            if (prePlayerIncludeCell == _playerIncludeCell) {
                return null
            }
            return _playerIncludeCell
        }
        set(value) {
            prePlayerIncludeCell = _playerIncludeCell
            _playerIncludeCell = value
        }
}
