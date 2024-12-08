package core.repository.money

import core.CoreModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MoneyRepositoryImplTest : KoinTest {
    private val moneyRepository: MoneyRepository by inject()

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

    @Test
    fun setMoney() {
        runBlocking {
            var count = 0

            val money = 10

            val collectJob = launch {
                moneyRepository.moneyStateFLow.collect {
                    assertEquals(
                        expected = money,
                        actual = it
                    )
                    count++
                }
            }

            moneyRepository.setMoney(money)

            delay(100)

            assertEquals(
                expected = 1,
                actual = count
            )

            collectJob.cancel()
        }
    }
}
