package gamescreen.map.usecase.resetnppc

import gamescreen.map.ModuleMap
import gamescreen.map.usecase.resetnpc.ResetNPCPositionUseCase
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class ResetNPCPositionUseCaseImplTest : KoinTest {
    val resetNPCPositionUseCase: ResetNPCPositionUseCase by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    // todo test作成
}
