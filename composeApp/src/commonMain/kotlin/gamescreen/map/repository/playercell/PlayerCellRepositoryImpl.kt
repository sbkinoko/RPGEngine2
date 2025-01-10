package gamescreen.map.repository.playercell

import gamescreen.map.domain.BackgroundCell

class PlayerCellRepositoryImpl : PlayerCellRepository {
    private var prePlayerIncludeCell: BackgroundCell? = null

    override var playerIncludeCell: BackgroundCell? = null
        get() {
            val preMapPoint = prePlayerIncludeCell?.mapPoint
            val mapPoint = field?.mapPoint

            //同一のマスないなら何も起こさない
            if (preMapPoint == mapPoint) {
                return null
            }

            //違うならなんでもいいから返す
            return field
        }
        set(value) {
            prePlayerIncludeCell = field
            field = value
        }
}
