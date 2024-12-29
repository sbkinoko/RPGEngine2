package gamescreen.battle.usecase.getdroptool

import common.status.MonsterStatusTest.Companion.getTestMonster
import core.ModuleCore
import core.domain.status.DropItemInfo
import core.domain.status.MonsterStatus
import core.repository.battlemonster.TestBattleMonsterRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class GetDropToolUseCaseImplTest {

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
     * 道具を確率で落とす場合のテスト
     */
    @Test
    fun dropToolPROB() {
        val itemId = 1
        val getDropToolUseCase: GetDropToolUseCase = GetDropToolUseCaseImpl(
            battleMonsterRepository = object : TestBattleMonsterRepository {
                override fun getMonsters(): List<MonsterStatus> {
                    return listOf(
                        getTestMonster().copy(
                            dropInfoList = listOf(
                                DropItemInfo(
                                    itemId = itemId,
                                    probability = 50,
                                )
                            )
                        )
                    )
                }
            }
        )

        val idList = List(10) {
            getDropToolUseCase.invoke()
        }

        assertTrue(
            actual = idList.contains(itemId)
        )
        assertTrue(
            actual = idList.contains(null)
        )
    }

    /**
     * 必ず道具を落とす場合のテスト
     */
    @Test
    fun dropToolABS() {
        val itemId = 1
        val getDropToolUseCase: GetDropToolUseCase = GetDropToolUseCaseImpl(
            battleMonsterRepository = object : TestBattleMonsterRepository {
                override fun getMonsters(): List<MonsterStatus> {
                    return listOf(
                        getTestMonster().copy(
                            dropInfoList = listOf(
                                DropItemInfo(
                                    itemId = itemId,
                                    probability = 100,
                                )
                            )
                        )
                    )
                }
            }
        )

        val idList = List(10) {
            getDropToolUseCase.invoke()
        }

        assertTrue(
            actual = idList.all {
                it == itemId
            }
        )
    }

    /**
     * 必ず道具を落とさない場合のテスト
     */
    @Test
    fun notDropToolABS() {
        val itemId = 1
        val getDropToolUseCase: GetDropToolUseCase = GetDropToolUseCaseImpl(
            battleMonsterRepository = object : TestBattleMonsterRepository {
                override fun getMonsters(): List<MonsterStatus> {
                    return listOf(
                        getTestMonster().copy(
                            dropInfoList = listOf(
                                DropItemInfo(
                                    itemId = itemId,
                                    probability = 0,
                                )
                            )
                        )
                    )
                }
            }
        )

        val idList = List(10) {
            getDropToolUseCase.invoke()
        }

        assertTrue(
            actual = idList.all {
                it == null
            }
        )
    }
}
