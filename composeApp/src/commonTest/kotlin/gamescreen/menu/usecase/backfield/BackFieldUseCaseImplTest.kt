package menu.usecase.backfield

import core.usecase.changetomap.ChangeToMapUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import menu.domain.MenuType
import menu.repository.menustate.MenuStateRepository
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackFieldUseCaseImplTest : KoinTest {
    lateinit var backFieldUseCase: CloseMenuUseCase

    private var repositoryCount = 0
    private var useCaseCount = 0

    @BeforeTest
    fun beforeTest() {
        backFieldUseCase = CloseMenuUseCaseImpl(
            menuStateRepository = object : MenuStateRepository {
                override val commandTypeFlow: MutableSharedFlow<MenuType>
                    get() = throw NotImplementedError()
                override var nowCommandType: MenuType
                    get() = throw NotImplementedError()
                    set(value) {
                        throw NotImplementedError()
                    }

                override fun push(commandType: MenuType) {
                    throw NotImplementedError()
                }

                override fun pop() {
                    throw NotImplementedError()
                }

                override fun reset() {
                    repositoryCount++
                }
            },
            changeToMapUseCase = object : ChangeToMapUseCase {
                override fun invoke() {
                    useCaseCount++
                }
            },
        )
    }


    @Test
    fun checkBackField() {
        runBlocking {
            backFieldUseCase()

            assertEquals(
                expected = 1,
                actual = repositoryCount,
            )

            assertEquals(
                expected = 1,
                actual = useCaseCount,
            )
        }
    }
}
