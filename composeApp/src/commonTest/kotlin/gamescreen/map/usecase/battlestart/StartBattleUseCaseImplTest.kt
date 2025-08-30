package gamescreen.map.usecase.battlestart

import core.ModuleCore
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.StatusData
import core.domain.status.StatusDataTest
import core.domain.status.monster.MonsterStatus
import core.repository.memory.character.battlemonster.BattleInfoRepository
import core.repository.memory.screentype.ScreenTypeRepository
import gamescreen.GameScreenType
import gamescreen.ModuleMain
import gamescreen.battle.domain.ActionData
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.battle.repository.attackeffect.AttackEffectInfo
import gamescreen.battle.repository.attackeffect.AttackEffectRepository
import gamescreen.battle.repository.commandstate.CommandStateRepository
import gamescreen.battle.repository.flash.FlashInfo
import gamescreen.battle.repository.flash.FlashRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StartBattleUseCaseImplTest : KoinTest {
    private lateinit var startBattleUseCase: StartBattleUseCase

    private val screenTypeRepository: ScreenTypeRepository by inject()
    private val eventRepository: core.repository.memory.event.EventRepository by inject()

    var checkCommand = 0
    var checkAction = 0
    var checkMonster = 0
    var checkMonsterStatus = 0
    var checkBackground = 0
    var flashCount = 0
    var effectCount = 0

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMain,
                ModuleCore,
            )
        }

        startBattleUseCase = StartBattleUseCaseImpl(
            battleInfoRepository = object : BattleInfoRepository {
                override val monsterListStateFLow: StateFlow<List<MonsterStatus>>
                    get() = throw NotImplementedError()

                override val backgroundType: StateFlow<BattleBackgroundType>
                    get() = throw NotImplementedError()

                override fun getStatus(id: Int): MonsterStatus {
                    throw NotImplementedError()
                }

                override suspend fun setStatusList(status: List<MonsterStatus>) {
                    throw NotImplementedError()
                }

                override fun getStatusList(): List<MonsterStatus> {
                    throw NotImplementedError()
                }

                override fun setMonsters(monsters: List<MonsterStatus>) {
                    checkMonster++
                }

                override fun setBackgroundType(backgroundType: BattleBackgroundType) {
                    checkBackground++
                }

                override suspend fun setStatus(
                    id: Int,
                    status: MonsterStatus,
                ) {
                    throw NotImplementedError()
                }

                override fun reload() {
                    throw NotImplementedError()
                }
            },
            screenTypeRepository = screenTypeRepository,
            commandStateRepository = object : CommandStateRepository {
                override val commandStateFlow: StateFlow<BattleCommandType>
                    get() = throw NotImplementedError()
                override val nowBattleCommandType: BattleCommandType
                    get() = throw NotImplementedError()

                override fun init() {
                    checkCommand++
                }

                override fun push(battleCommandType: BattleCommandType) {
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
                    itemId: Any?,
                    itemIndex: Int?,
                ) {
                    throw NotImplementedError()
                }

                override fun setTarget(
                    playerId: Int,
                    target: Int,
                ) {
                    throw NotImplementedError()
                }

                override fun setAlly(
                    playerId: Int,
                    allyId: Int,
                ) {
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
            eventRepository = eventRepository,
            flashRepository = object : FlashRepository {
                override val flashStateFlow: StateFlow<List<FlashInfo>>
                    get() = throw NotImplementedError()
                override var monsterNum: Int = 0
                    set(value) {
                        field = value
                        flashCount++
                    }

                override fun setInfo(list: List<FlashInfo>) {
                    throw NotImplementedError()
                }
            },
            attackEffectRepository = object : AttackEffectRepository {
                override val effectStateFlow: StateFlow<List<AttackEffectInfo>>
                    get() = throw NotImplementedError()
                override var monsterNum: Int = 0
                    set(value) {
                        field = value
                        effectCount++
                    }

                override fun setEffect(list: List<AttackEffectInfo>) {
                    throw NotImplementedError()
                }
            },
            statusDataRepository = object : core.repository.memory.character.statusdata.StatusDataRepository {
                override val statusDataFlow: StateFlow<List<StatusData>>
                    get() = throw NotImplementedError()

                override fun getStatusData(id: Int): StatusData {
                    throw NotImplementedError()
                }

                override fun getStatusList(): List<StatusData> {
                    throw NotImplementedError()
                }

                override fun setStatusList(statusList: List<StatusData>) {
                    checkMonsterStatus++
                }

                override fun setStatusData(
                    id: Int,
                    statusData: StatusData,
                ) {
                    throw NotImplementedError()
                }
            }
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkStart() {
        runBlocking {
            lateinit var result: GameScreenType
            val collectJob = launch {
                screenTypeRepository.screenStateFlow.collect {
                    result = it
                }
            }

            startBattleUseCase.invoke(
                monsterList = listOf(
                    Pair(
                        TestActiveMonster,
                        StatusDataTest.TestEnemyStatusActive,
                    )
                ),
                backgroundType = BattleBackgroundType.Road,
            )

            delay(100)

            assertEquals(
                actual = GameScreenType.BATTLE,
                expected = result,
            )

            assertEquals(
                expected = 1,
                actual = checkMonster,
            )

            assertEquals(
                expected = 1,
                actual = checkMonsterStatus,
            )

            assertEquals(
                expected = 1,
                actual = checkCommand,
            )

            assertEquals(
                expected = 1,
                actual = checkAction,
            )
            assertEquals(
                expected = 1,
                actual = checkBackground,
            )

            assertEquals(
                expected = 1,
                actual = flashCount
            )

            assertEquals(
                expected = 1,
                actual = effectCount
            )

            collectJob.cancel()
        }
    }
}
