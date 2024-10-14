package gamescreen.map.usecase.startbattle

import core.domain.ScreenType
import core.domain.status.MonsterStatus
import core.repository.battlemonster.BattleMonsterRepository
import core.repository.screentype.ScreenTypeRepository
import gamescreen.battle.domain.ActionData
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.repository.commandstate.CommandStateRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import main.MainModule
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

                override fun getStatus(id: Int): MonsterStatus {
                    throw NotImplementedError()
                }

                override fun getMonsters(): List<MonsterStatus> {
                    throw NotImplementedError()
                }

                override suspend fun setMonsters(monsters: List<MonsterStatus>) {
                    checkMonster++
                }

                override suspend fun setStatus(id: Int, status: MonsterStatus) {
                    throw NotImplementedError()
                }

                override suspend fun reload() {
                    throw NotImplementedError()
                }

            },
            screenTypeRepository = screenTypeRepository,
            commandStateRepository = object : CommandStateRepository {
                override val commandTypeFlow: MutableSharedFlow<BattleCommandType>
                    get() = throw NotImplementedError()
                override val nowCommandType: BattleCommandType
                    get() = throw NotImplementedError()

                override fun init() {
                    checkCommand++
                }

                override fun push(commandType: BattleCommandType) {
                    throw NotImplementedError()
                }

                override fun pop() {
                    throw NotImplementedError()
                }

                override fun popTo(condition: (BattleCommandType) -> Boolean) {
                    throw NotImplementedError()
                }

            },
            actionRepository = object : ActionRepository {
                override fun setAction(
                    playerId: Int,
                    actionType: ActionType,
                    itemId: Int?,
                    itemIndex: Int?,
                ) {
                    throw NotImplementedError()
                }

                override fun setTarget(playerId: Int, target: Int) {
                    throw NotImplementedError()
                }

                override fun setAlly(playerId: Int, allyId: Int) {
                    throw NotImplementedError()
                }

                override fun getAction(playerId: Int): ActionData {
                    throw NotImplementedError()
                }

                override fun getLastSelectAction(playerId: Int): ActionType {
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
