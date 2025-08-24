package core.repository.money

import core.ModuleCore
import core.repository.memory.money.MoneyLackException
import core.repository.memory.money.MoneyRepository
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

    /**
     * 初期状態のテスト
     */
    @Test
    fun initial() {
        assertEquals(
            expected = MoneyRepository.INITIAL_MONEY,
            actual = moneyRepository.moneyStateFLow.value
        )
    }

    /**
     * 値の反映のテスト
     */
    @Test
    fun setMoney() {
        runBlocking {
            var count = 0

            val money = 10

            val collectJob = launch {
                moneyRepository.moneyStateFLow.collect {
                    //想定した変更か確認
                    assertEquals(
                        expected = money,
                        actual = it
                    )
                    count++
                }
            }

            moneyRepository.setMoney(money)

            delay(100)

            //変更が行われたことを確認
            assertEquals(
                expected = 1,
                actual = count
            )

            collectJob.cancel()
        }
    }

    /**
     * お金の加算処理
     */
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

            //更新が1回であることを確認
            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    /**
     * お金の減算処理
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
                    when (count) {
                        0 -> assertEquals(
                            expected = startMoney,
                            actual = it
                        )

                        1 -> assertEquals(
                            expected = startMoney - money,
                            actual = it
                        )
                    }

                    count++
                }
            }

            delay(100)

            moneyRepository.decMoney(
                money,
            )

            delay(100)

            //初期値と減算の2回
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

            //初期値と減算の2回
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
                e: MoneyLackException,
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
