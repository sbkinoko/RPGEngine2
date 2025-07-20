package gamescreen.map.usecase.changeheight

import gamescreen.map.ModuleMap
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.ObjectHeightDetail
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
            val target = ObjectHeight.Water(height = ObjectHeightDetail.Mid)
            val player = gamescreen.map.domain.Player(size = 0f)
            val newPlayer = changeHeightUseCase.invoke(
                target,
                player = player
            )

            assertEquals(
                actual = newPlayer.square.objectHeight,
                expected = target,
            )
        }
    }

    @Test
    fun moveToGround() {
        runBlocking {
            val target = ObjectHeight.Ground(height = ObjectHeightDetail.High)

            val player = gamescreen.map.domain.Player(size = 0f)

            val newPlayer = changeHeightUseCase.invoke(
                target,
                player = player,
            )

            assertEquals(
                actual = newPlayer.square.objectHeight,
                expected = target,
            )
        }
    }
}
