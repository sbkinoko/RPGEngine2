package gamescreen.map.usecase.changeheight

import gamescreen.map.ModuleMap
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.repository.player.PlayerPositionRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ChangeHeightUseCaseImplTest : KoinTest {

    private val changeHeightUseCase: ChangeHeightUseCase by inject()
    private val playerPositionRepository: PlayerPositionRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun moveToWater() {
        runBlocking {
            val target = ObjectHeight.Water(height = 1)
            changeHeightUseCase.invoke(target)

            assertEquals(
                actual = playerPositionRepository.getPlayerPosition().square.objectHeight,
                expected = target,
            )
        }
    }

    @Test
    fun moveToGround() {
        runBlocking {
            val target = ObjectHeight.Ground(height = 2)
            changeHeightUseCase.invoke(target)

            assertEquals(
                actual = playerPositionRepository.getPlayerPosition().square.objectHeight,
                expected = target,
            )
        }
    }
}
