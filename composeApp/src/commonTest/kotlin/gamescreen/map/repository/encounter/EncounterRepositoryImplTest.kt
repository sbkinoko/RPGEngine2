package gamescreen.map.repository.encounter

import gamescreen.map.ModuleMap
import gamescreen.map.repository.encouter.EncounterRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class EncounterRepositoryImplTest : KoinTest {

    val encounterRepository: EncounterRepository by inject()

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
}
