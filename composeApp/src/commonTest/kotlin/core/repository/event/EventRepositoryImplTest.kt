package core.repository.event

import core.ModuleCore
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class EventRepositoryImplTest : KoinTest {
    val eventRepository = inject<EventRepository>()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleCore,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }
}
