package gamescreen.menu.usecase.givetool

import core.ModuleCore
import core.repository.player.PlayerStatusRepository
import data.ModuleData
import data.item.tool.ToolId
import gamescreen.menu.ModuleMenu
import gamescreen.menu.domain.BagToolData
import gamescreen.menu.domain.GiveResult
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.menu.repository.bag.BagRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import values.Constants
import values.TextData
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GiveToolUseCaseImplTest : KoinTest {
    private val playerStatusRepository: PlayerStatusRepository by inject()
    private val bagRepository: BagRepository by inject()
    private val targetRepository: TargetRepository by inject()
    private val userRepository: UserRepository by inject()
    private val indexRepository: IndexRepository by inject()

    private val giveToolUseCase: GiveToolUseCase by inject()

    private val fromPlayer = 0
    private val fromPlayerStatus
        get() = playerStatusRepository.getPlayers()[fromPlayer]

    private val toPlayer = 0
    private val toPlayerStatus
        get() = playerStatusRepository.getPlayers()[toPlayer]

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMenu,
                ModuleCore,
                ModuleData,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * 道具が所有上限だった場合のテスト
     */
    @Test
    fun fullTool() {
        runBlocking {
            //todo 袋から渡す処理を利用する
            // 渡す対象を設定
            val target = 0
            targetRepository.target = target

            val itemId = ToolId.HEAL1

            //　対象の持ち物をいっぱいにする
            val player1 = playerStatusRepository.getPlayers()[target]
            playerStatusRepository.setStatus(
                id = target,
                status = player1.copy(
                    toolList = List(Constants.MAX_TOOL_NUM) {
                        itemId
                    }
                ),
            )

            // 渡す人を設定
            val from = 1
            userRepository.userId = from

            val fromPlayer = playerStatusRepository.getPlayers()[from]
            playerStatusRepository.setStatus(
                id = from,
                status = fromPlayer.copy(
                    toolList = List(Constants.MAX_TOOL_NUM) {
                        itemId
                    }
                )
            )

            indexRepository.index = 0

            val result = giveToolUseCase.invoke()

            // todo dataclassなので全部assertEqualsでやる
            assertTrue(
                result is GiveResult.NG
            )

            assertEquals(
                expected = TextData.HAS_FULL_ITEM,
                actual = result.text,
            )
        }
    }

    @Test
    fun movePlayerToBag() {
        runBlocking {
            val id1 = ToolId.HEAL1
            val id2 = ToolId.HEAL2
            // 一番上を渡す
            val result = moveToolFromPlayerToBag(
                index = 0,
                itemList = listOf(
                    id1,
                    id2,
                )
            )

            assertEquals(
                expected = GiveResult.OK(
                    itemId = id1,
                ),
                actual = result
            )

            assertEquals(
                expected = listOf(
                    BagToolData(id = id1, num = 1)
                ),
                actual = bagRepository.getList()
            )

            assertEquals(
                expected = listOf(id2),
                actual = fromPlayerStatus.toolList
            )

            // 一番下を渡す
            val result2 = moveToolFromPlayerToBag(
                index = 1,
                itemList = listOf(
                    id1,
                    id2,
                )
            )

            assertEquals(
                expected = GiveResult.OK(
                    itemId = id2,
                ),
                actual = result2
            )

            assertEquals(
                expected = listOf(
                    BagToolData(id = id1, num = 1),
                    BagToolData(id = id2, num = 1),
                ),
                actual = bagRepository.getList()
            )

            assertEquals(
                expected = listOf(id1),
                actual = fromPlayerStatus.toolList
            )
        }
    }

    private suspend fun moveToolFromPlayerToBag(
        itemList: List<ToolId>,
        index: Int,
    ): GiveResult {
        // 渡される対象を袋に設定
        targetRepository.target = Constants.playerNum

        // 渡す人を設定
        userRepository.userId = fromPlayer

        // 渡す位置を設定
        indexRepository.index = index

        // 道具を設定
        playerStatusRepository.setStatus(
            id = fromPlayer,
            status = fromPlayerStatus.copy(
                toolList = itemList
            ),
        )

        // 渡す処理実施
        return giveToolUseCase.invoke()
    }

    @Test
    fun moveBagToPlayer() {
        runBlocking {
            playerStatusRepository.setStatus(
                id = toPlayer,
                status = toPlayerStatus.copy(
                    toolList = listOf()
                )
            )

            val id = ToolId.HEAL1
            val result1 = moveFromBagToPlayer(
                itemList = listOf(
                    BagToolData(id = id, num = 1),
                ),
                index = 0,
            )
            assertEquals(
                expected = GiveResult.OK(
                    itemId = id,
                ),
                actual = result1
            )
            assertEquals(
                expected = listOf(id),
                actual = toPlayerStatus.toolList,
            )
            assertEquals(
                expected = listOf(
                    BagToolData(
                        id = id,
                        num = 0,
                    )
                ),
                actual = bagRepository.getList(),
            )

            val id1 = ToolId.HEAL1
            val id2 = ToolId.HEAL2
            val result2 = moveFromBagToPlayer(
                itemList = listOf(
                    BagToolData(id = id1, num = 1),
                    BagToolData(id = id2, num = 2),
                ),
                index = 1,
            )

            assertEquals(
                expected = GiveResult.OK(
                    itemId = id2,
                ),
                actual = result2
            )
            assertEquals(
                expected = listOf(id, id2),
                actual = toPlayerStatus.toolList,
            )
            assertEquals(
                expected = listOf(
                    BagToolData(
                        id = id1,
                        num = 1,
                    ),
                    BagToolData(
                        id = id2,
                        num = 1,
                    )
                ),
                actual = bagRepository.getList(),
            )
        }
    }

    private suspend fun moveFromBagToPlayer(
        itemList: List<BagToolData>,
        index: Int,
    ): GiveResult {
        userRepository.userId = Constants.playerNum
        targetRepository.target = toPlayer
        indexRepository.index = index

        itemList.forEach {
            bagRepository.setData(it)
        }

        return giveToolUseCase.invoke()
    }
}
