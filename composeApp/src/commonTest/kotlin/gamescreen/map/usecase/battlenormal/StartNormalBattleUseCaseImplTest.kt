package gamescreen.map.usecase.battlenormal

import core.domain.BattleEventCallback
import core.domain.mapcell.CellType
import core.domain.mapcell.toBattleBackGround
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.StatusData
import core.domain.status.StatusDataTest
import core.domain.status.monster.MonsterStatus
import core.domain.testMapUiState
import core.usecase.restart.RestartUseCase
import data.usecase.battledecidemonster.DecideBattleMonsterUseCase
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.usecase.battlestart.StartBattleUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.flow.StateFlow
import kotlin.test.Test
import kotlin.test.assertEquals


//todo 敗北時の呼び出しを確認する
class StartNormalBattleUseCaseImplTest {
    private val textRepository = object : TextRepository {
        override val callBack: (() -> Unit)
            get() = throw NotImplementedError()
        override val text: String
            get() = throw NotImplementedError()
        override val textDataStateFlow: StateFlow<TextBoxData?>
            get() = throw NotImplementedError()
        override val nowTextData: TextBoxData
            get() = throw NotImplementedError()

        override fun push(textBoxData: TextBoxData) {
            throw NotImplementedError()
        }

        override fun push(textBoxDataList: List<TextBoxData>) {
            throw NotImplementedError()
        }

        override fun pop() {
            throw NotImplementedError()
        }
    }

    private val restartUseCase = object : RestartUseCase {
        override suspend fun invoke(mapUiState: MapUiState): MapUiState {
            throw NotImplementedError()
        }
    }

    private lateinit var decideBattleMonsterUseCase: DecideBattleMonsterUseCase
    private lateinit var playerCellRepository: PlayerCellRepository
    private lateinit var startBattleUseCase: StartBattleUseCase

    private val startNormalBattleUseCase: StartNormalBattleUseCase
        get() = StartNormalBattleUseCaseImpl(
            decideBattleMonsterUseCase = decideBattleMonsterUseCase,
            playerCellRepository = playerCellRepository,
            startBattleUseCase = startBattleUseCase,
            restartUseCase = restartUseCase,
            textRepository = textRepository
        )

    @Test
    fun notStart() {
        decideBattleMonsterUseCase = object : DecideBattleMonsterUseCase {
            override fun invoke(backgroundCell: BackgroundCell): List<Pair<MonsterStatus, StatusData>> {
                throw NotImplementedError()
            }
        }

        playerCellRepository = object : PlayerCellRepository {
            override val playerIncludeCellFlow: StateFlow<BackgroundCell?>
                get() = throw NotImplementedError()
            override var playerIncludeCell: BackgroundCell?
                get() = throw NotImplementedError()
                set(
                    @Suppress("UNUSED_PARAMETER")
                    value,
                ) {
                }
            override var playerCenterCell: BackgroundCell
                get() = BackgroundCell(
                    cellType = CellType.Town1Exit,
                    mapPoint = MapPoint(0, 0),
                    rectangle = NormalRectangle(x = 0f, y = 0f, size = 0f),
                    collisionData = emptyList(),
                    aroundCellId = emptyList(),
                )
                set(
                    @Suppress("UNUSED_PARAMETER")
                    value,
                ) {
                }
            override val eventCell: BackgroundCell
                get() = throw NotImplementedError()
        }

        startBattleUseCase = object : StartBattleUseCase {
            override fun invoke(
                monsterList: List<Pair<MonsterStatus, StatusData>>,
                battleEventCallback: BattleEventCallback,
                backgroundType: BattleBackgroundType,
            ) {
                NotImplementedError()
            }
        }

        startNormalBattleUseCase.invoke(
            testMapUiState,
        ) {}

        // エラーが起こらなければOK
    }

    @Test
    fun checkMonster() {
        val cellType = CellType.Road
        val expectedBackgroundCell = BackgroundCell(
            cellType = cellType,
            mapPoint = MapPoint(0, 0),
            rectangle = NormalRectangle(x = 0f, y = 0f, size = 0f),
            collisionData = emptyList(),
            aroundCellId = emptyList(),
        )
        var count = 0

        playerCellRepository = object : PlayerCellRepository {
            override val playerIncludeCellFlow: StateFlow<BackgroundCell?>
                get() = throw NotImplementedError()
            override var playerIncludeCell: BackgroundCell?
                get() = throw NotImplementedError()
                set(
                    @Suppress("UNUSED_PARAMETER")
                    value,
                ) {
                }
            override var playerCenterCell: BackgroundCell
                get() = expectedBackgroundCell
                set(
                    @Suppress("UNUSED_PARAMETER")
                    value,
                ) {
                }
            override val eventCell: BackgroundCell
                get() = throw NotImplementedError()
        }

        decideBattleMonsterUseCase = object : DecideBattleMonsterUseCase {
            override fun invoke(backgroundCell: BackgroundCell): List<Pair<MonsterStatus, StatusData>> {
                assertEquals(
                    expected = expectedBackgroundCell,
                    actual = backgroundCell,
                )
                count++
                return listOf()
            }
        }

        startBattleUseCase = object : StartBattleUseCase {
            override fun invoke(
                monsterList: List<Pair<MonsterStatus, StatusData>>,
                battleEventCallback: BattleEventCallback,
                backgroundType: BattleBackgroundType,
            ) {
            }
        }


        startNormalBattleUseCase.invoke(
            testMapUiState,
        ) {

        }

        assertEquals(
            expected = 1,
            actual = count,
        )
    }

    @Test
    fun checkStart() {
        val cellType = CellType.Road
        val expectedBackgroundCell = BackgroundCell(
            cellType = cellType,
            mapPoint = MapPoint(0, 0),
            rectangle = NormalRectangle(x = 0f, y = 0f, size = 0f),
            collisionData = emptyList(),
            aroundCellId = emptyList(),
        )

        val expectedBackground = cellType.toBattleBackGround()
        val expectedMonsterList = listOf(
            Pair(
                TestActiveMonster,
                StatusDataTest.TestEnemyStatusActive,
            ),
            Pair(
                TestActiveMonster,
                StatusDataTest.TestEnemyStatusActive,
            ),
        )
        var count = 0

        playerCellRepository = object : PlayerCellRepository {
            override val playerIncludeCellFlow: StateFlow<BackgroundCell?>
                get() = throw NotImplementedError()
            override var playerIncludeCell: BackgroundCell?
                get() = throw NotImplementedError()
                set(
                    @Suppress("UNUSED_PARAMETER")
                    value,
                ) {
                }
            override var playerCenterCell: BackgroundCell
                get() = expectedBackgroundCell
                set(
                    @Suppress("UNUSED_PARAMETER")
                    value,
                ) {
                }
            override val eventCell: BackgroundCell
                get() = throw NotImplementedError()
        }

        decideBattleMonsterUseCase = object : DecideBattleMonsterUseCase {
            override fun invoke(backgroundCell: BackgroundCell): List<Pair<MonsterStatus, StatusData>> {
                return expectedMonsterList
            }
        }

        startBattleUseCase = object : StartBattleUseCase {
            override fun invoke(
                monsterList: List<Pair<MonsterStatus, StatusData>>,
                battleEventCallback: BattleEventCallback,
                backgroundType: BattleBackgroundType,
            ) {
                assertEquals(
                    expected = expectedMonsterList,
                    actual = monsterList,
                )
                assertEquals(
                    expected = expectedBackground,
                    actual = backgroundType,
                )

                count++
            }
        }

        startNormalBattleUseCase.invoke(
            testMapUiState,
        ) {

        }

        assertEquals(
            expected = 1,
            actual = count,
        )
    }
}
