package gamescreen.map.usecase.decideconnectcype

import core.domain.mapcell.CellType
import gamescreen.map.domain.ConnectType

interface DecideConnectTypeUseCase {

    /**
     * 3*3のフィールドを受け取って接続の種類をチェックする
     */
    operator fun invoke(
        cellList: List<List<CellType>>,
    ): ConnectType
}
