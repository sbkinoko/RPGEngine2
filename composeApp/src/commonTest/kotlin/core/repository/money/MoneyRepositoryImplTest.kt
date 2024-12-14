package core.repository.money

import core.ModuleCore
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
                ModuleCore,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun initial() {
        assertEquals(
            expected = MoneyRepository.INITIAL_MONEY,
            actual = moneyRepository.moneyStateFLow.value
        )
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

    @Test
    fun addMoney() {
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

            moneyRepository.addMoney(
                money,
            )

            delay(100)

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    /**
     * お金の減産処理
     * 0より大きい
     */
    @Test
    fun decMoneyPlus() {
        runBlocking {
            var count = 0
            val startMoney = 10

            moneyRepository.setMoney(
                startMoney
            )

            delay(100)

            val money = 5
            val collectJob = launch {
                moneyRepository.moneyStateFLow.collect {
                    assertEquals(
                        expected = startMoney - money,
                        actual = it
                    )
                    count++
                }
            }

            moneyRepository.decMoney(
                money,
            )

            delay(100)

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }


    /**
     * お金の減産処理
     * 0
     */
    @Test
    fun decMoney0() {
        runBlocking {
            var count = 0
            val startMoney = 10

            moneyRepository.setMoney(
                startMoney
            )

            delay(100)

            val collectJob = launch {
                moneyRepository.moneyStateFLow.collect {
                    count++
                    when (count) {
                        1 -> assertEquals(
                            expected = startMoney,
                            actual = it,
                        )

                        2 -> assertEquals(
                            expected = 0,
                            actual = it,
                        )
                    }
                }
            }

            delay(100)

            moneyRepository.decMoney(
                startMoney,
            )

            delay(100)

            assertEquals(
                expected = 2,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    /**
     * お金の減産処理
     * 0
     */
    @Test
    fun decMoneyMinus() {
        runBlocking {
            var count = 0
            val startMoney = 10
            var errorCount = 0

            moneyRepository.setMoney(
                startMoney
            )

            delay(100)

            val decMoney = 20

            val collectJob = launch {
                moneyRepository.moneyStateFLow.collect {
                    count++
                }
            }

            delay(100)

            try {
                moneyRepository.decMoney(
                    decMoney,
                )
            } catch (
                e: MoneyLackException
            ) {
                errorCount++
            }

            delay(100)

            //setの1回
            assertEquals(
                expected = 1,
                actual = count,
            )

            //エラーの1回
            assertEquals(
                expected = 1,
                actual = errorCount,
            )

            collectJob.cancel()
        }
    }
}
