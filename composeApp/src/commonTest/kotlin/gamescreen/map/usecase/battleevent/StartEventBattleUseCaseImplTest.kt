package gamescreen.map.usecase.battleevent

import core.domain.BattleEventCallback
import core.domain.status.StatusData
import core.domain.status.monster.MonsterStatus
import data.battle.BattleDataRepository
import data.battle.EventBattleData
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.battle.domain.BattleId
import gamescreen.map.usecase.battlestart.StartBattleUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

class StartEventBattleUseCaseImplTest {
    private var count = 0

    private val battleId = BattleId.Battle1

    private val map = mutableMapOf<BattleId, Int>()
    private val eventBattleData = EventBattleData(
        monsterList = listOf(),
        battleEventCallback = BattleEventCallback(
            winCallback = {},
            loseCallback = {},
            escapeCallback = {},
        ),
        battleBackgroundType = BattleBackgroundType.Road,
    )

    private val battleDataRepository = object : BattleDataRepository {
        override fun getBattleMonsterData(battleId: BattleId): EventBattleData {
            map[battleId] = 1
            return eventBattleData
        }
    }

    private val startBattleUseCase = object : StartBattleUseCase {
        override fun invoke(
            monsterList: List<Pair<MonsterStatus, StatusData>>,
            battleEventCallback: BattleEventCallback,
            backgroundType: BattleBackgroundType,
        ) {
            assertEquals(
                expected = eventBattleData.monsterList,
                actual = monsterList,
            )

            assertEquals(
                expected = eventBattleData.battleEventCallback,
                actual = battleEventCallback,
            )

            assertEquals(
                expected = eventBattleData.battleBackgroundType,
                actual = backgroundType
            )
            count++
        }
    }

    private val startEventBattleUseCase = StartEventBattleUseCaseImpl(
        battleDataRepository, startBattleUseCase
    )

    @Test
    fun startEBattle() {
        startEventBattleUseCase.invoke(
            battleId = battleId
        )

        assertEquals(
            expected = 1,
            actual = count,
        )

        assertEquals(
            expected = 1,
            actual = map[battleId],
        )
    }
}
