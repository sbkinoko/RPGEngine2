package map.usecase.startbattle

import battle.domain.ActionData
import battle.domain.CommandType
import battle.repository.action.ActionRepository
import battle.repository.battlemonster.BattleMonsterRepository
import battle.repository.commandstate.CommandStateRepository
import common.status.MonsterStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import main.MainModule
import main.domain.ScreenType
import main.repository.screentype.ScreenTypeRepository
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StartBattleUseCaseImplTest : KoinTest {
    lateinit var startBattleUseCase: StartBattleUseCase

    private val screenTypeRepository: ScreenTypeRepository by inject()

    var checkCommand = 0
    var checkAction = 0
    var checkMonster = 0

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MainModule,
            )
        }

        startBattleUseCase = StartBattleUseCaseImpl(
            battleMonsterRepository = object : BattleMonsterRepository {
                override val monsterListFlow: MutableSharedFlow<List<MonsterStatus>>
                    get() = throw NotImplementedError()

                override fun getMonster(id: Int): MonsterStatus {
                    throw NotImplementedError()
                }

                override fun getMonsters(): List<MonsterStatus> {
                    throw NotImplementedError()
                }

                override suspend fun setMonster(monsters: List<MonsterStatus>) {
                    checkMonster++
                }

                override suspend fun reload() {
                    throw NotImplementedError()
                }

            },
            screenTypeRepository = screenTypeRepository,
            commandStateRepository = object : CommandStateRepository {
                override val commandTypeFlow: MutableSharedFlow<CommandType>
                    get() = throw NotImplementedError()
                override val nowCommandType: CommandType
                    get() = throw NotImplementedError()

                override fun init() {
                    checkCommand++
                }

                override fun push(commandType: CommandType) {
                    throw NotImplementedError()
                }

                override fun pop() {
                    throw NotImplementedError()
                }

            },
            actionRepository = object : ActionRepository {
                override fun setAction(playerId: Int, target: List<Int>) {
                    throw NotImplementedError()
                }

                override fun getAction(playerId: Int): ActionData {
                    throw NotImplementedError()
                }

                override fun resetTarget() {
                    checkAction++
                }
            },
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkStart() {

        runBlocking {
            startBattleUseCase()

            delay(100)

            assertEquals(
                actual = ScreenType.BATTLE,
                expected = screenTypeRepository.screenType,
            )

            assertEquals(
                expected = 1,
                actual = checkMonster,
            )

            assertEquals(
                expected = 1,
                actual = checkCommand,
            )

            assertEquals(
                expected = 1,
                actual = checkAction,
            )
        }
    }
}
