package map.usecase

import map.MapModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class IsCollidedUseCaseTest : KoinTest {

    private val useCase: IsCollidedUseCase by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MapModule,
            )
        }

        // todo 十字型に当たり判定を用意する
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    //todo 12パターンのテストを作る
}
