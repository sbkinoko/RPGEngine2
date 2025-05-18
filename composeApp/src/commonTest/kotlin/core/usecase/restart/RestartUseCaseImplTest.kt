package core.usecase.restart

import core.domain.testMapUiState
import core.usecase.heal.MaxHealUseCase
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
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
            mapId: Int,
            playerHeight: ObjectHeight,
            player: Player,
        ): MapUiState {
            roadMapCount++
            return testMapUiState
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
            restartUseCase.invoke(
                testMapUiState
            )

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
