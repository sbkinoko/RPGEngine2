package core.usecase.restart

import core.usecase.heal.MaxHealUseCase
import gamescreen.map.data.MapData
import gamescreen.map.domain.UIData
import gamescreen.map.usecase.roadmap.RoadMapUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RestartUseCaseImplTest {
    private lateinit var restartUseCase: RestartUseCase

    var roadMapCount = 0
    private val roadMapUseCase = object : RoadMapUseCase {
        override suspend fun invoke(
            mapX: Int,
            mapY: Int,
            mapData: MapData,
        ): UIData {
            roadMapCount++
            return UIData()
        }
    }

    var maxHealCount = 0
    private val maxHealUseCase = object : MaxHealUseCase {
        override suspend fun invoke() {
            maxHealCount++
        }
    }

    @BeforeTest
    fun beforeTest() {
        restartUseCase = RestartUseCaseImpl(
            roadMapUseCase = roadMapUseCase,
            maxHealUseCase = maxHealUseCase,
        )
    }

    @Test
    fun checkCall() {
        runBlocking {
            restartUseCase.invoke()

            assertEquals(
                expected = 1,
                actual = maxHealCount,
            )

            assertEquals(
                expected = 1,
                actual = roadMapCount,
            )
        }
    }
}
