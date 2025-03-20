package gamescreen.map.usecase.collision.list

import core.domain.mapcell.CellType
import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.repository.collision.CollisionRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCollisionListUseCaseImplTest {

    @Test
    fun call1() {
        val squareValue = NormalSquare(
            x = 0f,
            y = 0f,
            size = 10f,
        )
        val cellTypeValue = CellType.Null

        callUseCase(
            squareValue = squareValue,
            cellTypeValue = cellTypeValue,
        )
    }

    @Test
    fun call2() {
        val squareValue = NormalSquare(
            x = 10f,
            y = 10f,
            size = 10f,
        )
        val cellTypeValue = CellType.Road

        callUseCase(
            squareValue = squareValue,
            cellTypeValue = cellTypeValue,
        )
    }

    /**
     * 背景セルを作成して、当たり判定を取得するテスト
     */
    private fun callUseCase(
        squareValue: NormalSquare,
        cellTypeValue: CellType,
    ) {
        val backgroundCell = BackgroundCell(
            cellType = cellTypeValue,
            mapPoint = MapPoint(),
            square = squareValue,
        )

        var count = 0

        val collisionRepository = object : CollisionRepository {
            override fun collisionData(
                cellType: CellType,
                square: Rectangle,
            ): List<ShapeCollisionDetect> {
                assertEquals(
                    expected = cellTypeValue,
                    actual = cellType,
                )

                assertEquals(
                    expected = squareValue,
                    actual = square,
                )

                count++

                return emptyList()
            }
        }

        val useCase = GetCollisionListUseCaseImpl(
            collisionRepository = collisionRepository,
        )

        useCase.invoke(
            backgroundCell = backgroundCell,
        )

        assertEquals(
            expected = 1,
            actual = count,
        )
    }
}
