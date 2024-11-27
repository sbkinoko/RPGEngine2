package core.usecase.item.useskill

import core.CoreModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class UseSkillUseCaseImplTest : KoinTest {
    private val useSkillUseCase: UseSkillUseCase by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                CoreModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }
}
