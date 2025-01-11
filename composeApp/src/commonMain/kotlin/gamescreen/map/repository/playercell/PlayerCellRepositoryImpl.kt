package gamescreen.map.repository.playercell

import gamescreen.map.domain.BackgroundCell

class PlayerCellRepositoryImpl : PlayerCellRepository {
    private var prePlayerIncludeCell: BackgroundCell? = null

    override var playerIncludeCell: BackgroundCell? = null
        set(value) {
            prePlayerIncludeCell = field
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
