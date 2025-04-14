package gamescreen.map.usecase.resetposition

import gamescreen.map.ModuleMap
import gamescreen.map.data.LoopTestMap
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.manager.CELL_NUM
import gamescreen.map.manager.SIDE_LENGTH
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ResetBackgroundPositionUseCaseTest : KoinTest {
    private val mapData = LoopTestMap()

    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase
            by inject()
    private val repository: BackgroundRepository by inject()
    private lateinit var initializedData: BackgroundData

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap,
            )
        }

        repository.cellNum = CELL_NUM
        repository.screenSize = SIDE_LENGTH
        initializedData = resetBackgroundPositionUseCase.invoke(
            mapData = mapData,
            mapX = 0,
            mapY = 0,
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * 初期状態の確認
     */
    @Test
    fun initPosition() {
        initializedData.fieldData[0][0].apply {
            rectangle.apply {
                assertEquals(
                    0f,
                    x,
                )
                assertEquals(
                    0f,
                    y,
                )
            }
            mapPoint.apply {
                assertEquals(
                    3,
                    x,
                )
                assertEquals(
                    3,
                    y,
                )
            }
        }
    }

    @Test
    fun resetPosition() {
        val newData = resetBackgroundPositionUseCase.invoke(
            mapData = mapData,
            mapX = 1,
            mapY = 1,
        )

        newData.fieldData[0][0].apply {
            rectangle.apply {
                assertEquals(
                    0f,
                    x,
                )
                assertEquals(
                    0f,
                    y,
                )
            }

            mapPoint.apply {
                assertEquals(
                    0,
                    x,
                )
                assertEquals(
                    0,
                    y,
                )
            }
        }
    }
}
