package menu.usecase.backfield

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import main.MainModule
import main.domain.ScreenType
import main.repository.screentype.ScreenTypeRepository
import menu.domain.MenuType
import menu.repository.menustate.MenuStateRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackFieldUseCaseImplTest : KoinTest {
    lateinit var backFieldUseCase: BackFieldUseCase

    private val screenTypeRepository: ScreenTypeRepository by inject()

    private var count = 0

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MainModule,
            )
        }

        backFieldUseCase = BackFieldUseCaseImpl(
            screenTypeRepository = screenTypeRepository,
            menuStateRepository = object : MenuStateRepository {
                override val menuTypeFlow: MutableSharedFlow<MenuType>
                    get() = throw NotImplementedError()
                override var menuType: MenuType
                    get() = throw NotImplementedError()
                    set(value) {
                        throw NotImplementedError()
                    }

                override fun pop() {
                    throw NotImplementedError()
                }

                override fun reset() {
                    count++
                }
            },
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkBackField() {
        runBlocking {
            backFieldUseCase()

            assertEquals(
                expected = 1,
                actual = count,
            )

            assertEquals(
                expected = ScreenType.FIELD,
                actual = screenTypeRepository.screenType,
            )
        }
    }
}
