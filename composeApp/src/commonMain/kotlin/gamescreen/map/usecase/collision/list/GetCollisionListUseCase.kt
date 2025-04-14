package gamescreen.map.usecase.collision.list

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.collision.square.Rectangle

interface GetCollisionListUseCase {

    operator fun invoke(
        rectangle: Rectangle,
        cellType: CellType,
    ): List<ShapeCollisionDetect>
}
