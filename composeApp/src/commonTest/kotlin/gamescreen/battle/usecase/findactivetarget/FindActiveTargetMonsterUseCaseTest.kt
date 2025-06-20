package gamescreen.battle.usecase.findactivetarget

import core.domain.status.StatusDataTest
import gamescreen.battle.ModuleBattle
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FindActiveTargetMonsterUseCaseTest : KoinTest {
    private val findActiveTargetUseCase: FindActiveTargetUseCase by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleBattle,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * 指定した対象から、必要な数だけ選ばれることを確認
     */
    @Test
    fun findNormal() {
        val list = listOf(
            StatusDataTest.TestEnemyStatusActive,
            StatusDataTest.TestEnemyStatusActive,
            StatusDataTest.TestEnemyStatusActive,
        )

        findActiveTargetUseCase(
            statusList = list,
            target = 0,
            targetNum = 2,
        ).let {
            assertEquals(
                expected = listOf(0, 1),
                actual = it,
            )
        }
    }

    /**
     * 途中に倒れている対象がいた場合、その対象をスキップして選択することを確認
     */
    @Test
    fun findSkip() {
        val list = listOf(
            StatusDataTest.TestEnemyStatusActive,
            StatusDataTest.TestEnemyStatusInActive,
            StatusDataTest.TestEnemyStatusActive,
        )

        findActiveTargetUseCase(
            statusList = list,
            target = 0,
            targetNum = 2,
        ).let {
            assertEquals(
                expected = listOf(0, 2),
                actual = it,
            )
        }
    }

    /**
     * 対象にとった最初の一体が倒れていた場合に、次から選択されることを確認
     */
    @Test
    fun findNext() {
        val list = listOf(
            StatusDataTest.TestEnemyStatusInActive,
            StatusDataTest.TestEnemyStatusActive,
            StatusDataTest.TestEnemyStatusActive,
        )

        findActiveTargetUseCase(
            statusList = list,
            target = 0,
            targetNum = 2,
        ).let {
            assertEquals(
                expected = listOf(1, 2),
                actual = it,
            )
        }
    }

    /**
     * 複数選択したいのに一体しかいない場合、一体しか選ばれないことを確認
     */
    @Test
    fun findOne() {
        val list = listOf(
            StatusDataTest.TestEnemyStatusActive,
            StatusDataTest.TestEnemyStatusInActive,
            StatusDataTest.TestEnemyStatusInActive,
        )

        findActiveTargetUseCase(
            statusList = list,
            target = 0,
            targetNum = 2,
        ).let {
            assertEquals(
                expected = listOf(0),
                actual = it,
            )
        }
    }

    /**
     * 3体目を選んだ場合に最後と最初が選ばれることを確認
     */
    @Test
    fun findLoop() {
        val list = listOf(
            StatusDataTest.TestEnemyStatusActive,
            StatusDataTest.TestEnemyStatusActive,
            StatusDataTest.TestEnemyStatusActive,
        )

        findActiveTargetUseCase(
            statusList = list,
            target = 2,
            targetNum = 2,
        ).let {
            assertEquals(
                expected = listOf(2, 0),
                actual = it,
            )
        }
    }

    /**
     * 対象の総数より対象に取りたい数のほうが大きい場合、対象の総数しか選ばれないことを確認
     */
    @Test
    fun findOver() {
        val list = listOf(
            StatusDataTest.TestEnemyStatusActive,
            StatusDataTest.TestEnemyStatusActive,
            StatusDataTest.TestEnemyStatusActive,
        )

        findActiveTargetUseCase(
            statusList = list,
            target = 0,
            targetNum = 4,
        ).let {
            assertEquals(
                expected = listOf(0, 1, 2),
                actual = it,
            )
        }
    }
}
