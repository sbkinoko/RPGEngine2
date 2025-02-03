package gamescreen.map.repository.encounter

import gamescreen.map.ModuleMap
import gamescreen.map.repository.encouter.EncounterRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import values.GameParams
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class EncounterRepositoryImplTest : KoinTest {

    private val encounterRepository: EncounterRepository by inject()

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

    /**
     * 規定量に満たない移動の場合、戦闘が始まらない
     */
    @Test
    fun checkNotEncounter() {
        var battleCount = 0
        repeat(TEST_NUM) {
            if (encounterRepository.judgeStartBattle(
                    GameParams.ENCOUNTER_DISTANCE - 1f
                )
            ) {
                battleCount++
            }

            encounterRepository.resetCount()
        }

        assertEquals(
            expected = 0,
            actual = battleCount,
        )
    }

    @Test
    fun checkEncounter() {
        var battleCount = 0
        repeat(TEST_NUM) {
            if (encounterRepository.judgeStartBattle(
                    GameParams.ENCOUNTER_DISTANCE.toFloat(),
                )
            ) {
                battleCount++
            }

            encounterRepository.resetCount()
        }

        assertTrue {
            battleCount.toFloat() in
                    EXPECTED_BATTLE_NUM * LOW..EXPECTED_BATTLE_NUM * HIGH
        }
        assertNotEquals(
            illegal = 0,
            actual = battleCount,
        )
    }

    @Test
    fun checkEncounterDiv() {
        var battleCount = 0
        repeat(TEST_NUM) {
            if (encounterRepository.judgeStartBattle(
                    GameParams.ENCOUNTER_DISTANCE.toFloat() - 10f,
                )
            ) {
                battleCount++
            }

            if (encounterRepository.judgeStartBattle(
                    GameParams.ENCOUNTER_DISTANCE.toFloat() - 10f,
                )
            ) {
                battleCount++
            }

            encounterRepository.resetCount()
        }

        assertTrue {
            battleCount.toFloat() in
                    EXPECTED_BATTLE_NUM * LOW..EXPECTED_BATTLE_NUM * HIGH
        }
    }

    companion object {
        private const val TEST_NUM = 1000
        private const val EXPECTED_BATTLE_NUM = TEST_NUM * GameParams.ENCOUNTER_PROBABILITY / 100
        private const val LOW = 0.8f
        private const val HIGH = 1.2f
    }
}
