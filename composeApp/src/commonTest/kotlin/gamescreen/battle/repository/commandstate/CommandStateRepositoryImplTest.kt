package gamescreen.battle.repository.commandstate

import gamescreen.battle.domain.MainCommand
import gamescreen.battle.domain.PlayerActionCommand
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandStateRepositoryImplTest {
    private lateinit var repository: CommandStateRepository

    @BeforeTest
    fun beforeTest() {
        repository = CommandStateRepositoryImpl()
    }

    @Test
    fun initialState() {
        assertEquals(
            expected = CommandStateRepository.INITIAL_COMMAND_STATE,
            actual = repository.nowCommandType
        )
    }

    @Test
    fun pushState() {
        val playerID = 1
        val playerCommand = PlayerActionCommand(
            playerId = playerID,
        )

        runBlocking {
            var count1 = 0
            val collectJob = launch {
                repository.commandTypeFlow.collect {
                    count1++
                }
            }

            var count2 = 0
            val collectJob2 = launch {
                repository.commandStateFlow.collect {
                    count2++
                }
            }

            repository.push(
                playerCommand,
            )

            delay(100)

            assertEquals(
                expected = playerCommand,
                actual = repository.nowCommandType
            )

            assertEquals(
                expected = 1,
                actual = count1
            )
            assertEquals(
                expected = 1,
                actual = count2
            )

            val playerID2 = 2
            val playerCommand2 = PlayerActionCommand(
                playerId = playerID2,
            )

            repository.push(
                playerCommand2,
            )

            delay(100)

            assertEquals(
                expected = playerCommand2,
                actual = repository.nowCommandType
            )

            assertEquals(
                expected = 2,
                actual = count1
            )
            assertEquals(
                expected = 2,
                actual = count2
            )

            collectJob.cancel()
            collectJob2.cancel()
        }
    }

    @Test
    fun popState() {
        runBlocking {
            var count1 = 0
            val collectJob = launch {
                repository.commandTypeFlow.collect {
                    count1++
                }
            }

            var count2 = 0
            val collectJob2 = launch {
                repository.commandStateFlow.collect {
                    count2++
                }
            }

            val playerID = 1
            val playerCommand = PlayerActionCommand(
                playerId = playerID,
            )
            repository.push(
                playerCommand,
            )

            delay(100)

            val playerID2 = 2
            val playerCommand2 = PlayerActionCommand(
                playerId = playerID2,
            )

            repository.push(
                playerCommand2,
            )

            delay(100)

            assertEquals(
                expected = 2,
                actual = count1,
            )
            assertEquals(
                expected = 2,
                actual = count2,
            )

            repository.pop()

            delay(100)

            assertEquals(
                expected = 3,
                actual = count1,
            )
            assertEquals(
                expected = 3,
                actual = count2,
            )

            assertEquals(
                expected = playerCommand,
                actual = repository.nowCommandType,
            )

            repository.pop()

            delay(100)

            assertEquals(
                expected = 4,
                actual = count1
            )
            assertEquals(
                expected = 4,
                actual = count2
            )

            assertEquals(
                expected = MainCommand,
                actual = repository.nowCommandType,
            )

            collectJob.cancel()
            collectJob2.cancel()
        }
    }

    @Test
    fun checkInit() {
        runBlocking {
            var count1 = 0
            val collectJob = launch {
                repository.commandTypeFlow.collect {
                    count1++
                }
            }
            var count2 = 0
            val collectJob2 = launch {
                repository.commandStateFlow.collect {
                    count2++
                }
            }

            // init前の状態を作成
            val playerID = 1
            val playerCommand = PlayerActionCommand(
                playerId = playerID,
            )
            repository.push(
                playerCommand,
            )
            delay(100)

            val playerID2 = 2
            val playerCommand2 = PlayerActionCommand(
                playerId = playerID2,
            )
            repository.push(
                playerCommand2,
            )
            delay(100)

            assertEquals(
                expected = 2,
                actual = count1,
            )
            assertEquals(
                expected = 2,
                actual = count2,
            )

            repository.init()
            delay(100)

            //　initで変化したことを確認
            assertEquals(
                expected = 3,
                actual = count1,
            )
            assertEquals(
                expected = 3,
                actual = count2,
            )

            assertEquals(
                expected = MainCommand,
                actual = repository.nowCommandType,
            )

            //　initしたあとはpopしても変化がないことを確認
            repository.pop()

            delay(100)

            assertEquals(
                expected = 3,
                actual = count1,
            )
            assertEquals(
                expected = 3,
                actual = count2,
            )

            assertEquals(
                expected = MainCommand,
                actual = repository.nowCommandType,
            )

            collectJob.cancel()
            collectJob2.cancel()
        }
    }

    @Test
    fun popTo() {
        runBlocking {
            var count1 = 0
            val collectJob = launch {
                repository.commandTypeFlow.collect {
                    count1++
                }
            }

            var count2 = 0
            val collectJob2 = launch {
                repository.commandStateFlow.collect {
                    count2++
                }
            }


            //mainのままなのでcountに加算はなし
            repository.init()

            //1人目のアクション
            val first = PlayerActionCommand(
                playerId = 1,
            )
            repository.push(
                first,
            )

            delay(100)

            repository.push(
                PlayerActionCommand(
                    playerId = 2,
                )
            )

            delay(100)
            repository.push(
                PlayerActionCommand(
                    playerId = 3
                )
            )

            delay(100)

            assertEquals(
                expected = 3,
                actual = count1,
            )

            repository.popTo {
                it is PlayerActionCommand &&
                        it.playerId == 1
            }

            delay(100)

            assertEquals(
                expected = 4,
                actual = count1,
            )
            assertEquals(
                expected = 4,
                actual = count2,
            )

            assertEquals(
                actual = first,
                expected = repository.nowCommandType,
            )

            collectJob.cancel()
            collectJob2.cancel()
        }
    }

    @Test
    fun popToMain() {
        runBlocking {
            var count1 = 0
            val collectJob = launch {
                repository.commandTypeFlow.collect {
                    count1++
                }
            }

            var count2 = 0
            val collectJob2 = launch {
                repository.commandTypeFlow.collect {
                    count2++
                }
            }

            //mainのままなのでcountに加算はなし
            repository.init()

            //1人目のアクション
            val first = PlayerActionCommand(
                playerId = 1,
            )
            repository.push(
                first,
            )

            delay(100)

            repository.push(
                PlayerActionCommand(
                    playerId = 2,
                )
            )

            delay(100)
            repository.push(
                PlayerActionCommand(
                    playerId = 3
                )
            )

            delay(100)

            assertEquals(
                expected = 3,
                actual = count1,
            )
            assertEquals(
                expected = 3,
                actual = count2,
            )

            repository.popTo {
                it is PlayerActionCommand &&
                        it.playerId == 0
            }

            delay(100)

            assertEquals(
                expected = 4,
                actual = count1,
            )
            assertEquals(
                expected = 4,
                actual = count2,
            )

            assertEquals(
                actual = MainCommand,
                expected = repository.nowCommandType,
            )

            collectJob.cancel()
            collectJob2.cancel()
        }
    }
}
