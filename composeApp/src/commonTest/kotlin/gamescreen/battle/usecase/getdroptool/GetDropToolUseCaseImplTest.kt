package gamescreen.battle.usecase.getdroptool

import core.ModuleCore
import core.domain.status.DropItemInfo
import core.domain.status.MonsterStatus
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
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
                        TestActiveMonster.copy(
                            dropInfoList = listOf(
                                DropItemInfo(
                                    itemId = itemId,
                                    probability = 50,
                                ),
                            ),
                        )
                    )
                }
            }
        )

        val idList = List(10) {
            getDropToolUseCase.invoke()
        }

        assertTrue(
            actual = idList.any {
                it.size == 1 &&
                        it.contains(itemId)
            }
        )
        assertTrue(
            actual = idList.any {
                it.isEmpty()
            }
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
                        TestActiveMonster.copy(
                            dropInfoList = listOf(
                                DropItemInfo(
                                    itemId = itemId,
                                    probability = 100,
                                ),
                            ),
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
                it.size == 1 &&
                        it.contains(itemId)
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
                        TestActiveMonster.copy(
                            dropInfoList = listOf(
                                DropItemInfo(
                                    itemId = itemId,
                                    probability = 0,
                                ),
                            ),
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
                it.isEmpty()
            }
        )
    }

    /**
     * 複数の道具をドロップする場合のテスト
     */
    @Test
    fun dropToolSomeTool() {
        val itemId1 = 1
        val itemId2 = 2

        val getDropToolUseCase: GetDropToolUseCase = GetDropToolUseCaseImpl(
            battleMonsterRepository = object : TestBattleMonsterRepository {
                override fun getMonsters(): List<MonsterStatus> {
                    return listOf(
                        TestActiveMonster.copy(
                            dropInfoList = listOf(
                                DropItemInfo(
                                    itemId = itemId1,
                                    probability = 50,
                                ),
                                DropItemInfo(
                                    itemId = itemId2,
                                    probability = 50,
                                ),
                            ),
                        )
                    )
                }
            }
        )

        val idList = List(20) {
            getDropToolUseCase.invoke()
        }

        // 空
        assertTrue(
            actual = idList.any {
                it.isEmpty()
            }
        )

        //1だけ
        assertTrue(
            actual = idList.any {
                it.size == 1 &&
                        it.contains(itemId1)
            }
        )

        //2だけ
        assertTrue(
            actual = idList.any {
                it.size == 1 &&
                        it.contains(itemId2)
            }
        )


        //どっちも
        assertTrue(
            actual = idList.any {
                it.size == 2 &&
                        it.contains(itemId1) &&
                        it.contains(itemId2)
            }
        )
    }

    /**
     * 複数のモンスターが道具をドロップする場合のテスト
     */
    @Test
    fun dropToolSomeEnemy() {
        val itemId1 = 1
        val itemId2 = 2

        val getDropToolUseCase: GetDropToolUseCase = GetDropToolUseCaseImpl(
            battleMonsterRepository = object : TestBattleMonsterRepository {
                override fun getMonsters(): List<MonsterStatus> {
                    return listOf(
                        TestActiveMonster.copy(
                            dropInfoList = listOf(
                                DropItemInfo(
                                    itemId = itemId1,
                                    probability = 50,
                                ),
                            ),
                        ),
                        TestActiveMonster.copy(
                            dropInfoList = listOf(
                                DropItemInfo(
                                    itemId = itemId2,
                                    probability = 50,
                                ),
                            ),
                        ),
                    )
                }
            }
        )

        val idList = List(20) {
            getDropToolUseCase.invoke()
        }

        // 空
        assertTrue(
            actual = idList.any {
                it.isEmpty()
            }
        )

        //1だけ
        assertTrue(
            actual = idList.any {
                it.size == 1 &&
                        it.contains(itemId1)
            }
        )

        //2だけ
        assertTrue(
            actual = idList.any {
                it.size == 1 &&
                        it.contains(itemId2)
            }
        )

        //どっちも
        assertTrue(
            actual = idList.any {
                it.size == 2 &&
                        it.contains(itemId1) &&
                        it.contains(itemId2)
            }
        )
    }
}
