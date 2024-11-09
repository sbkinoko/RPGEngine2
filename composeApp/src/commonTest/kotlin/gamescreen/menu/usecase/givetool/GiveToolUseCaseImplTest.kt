package gamescreen.menu.usecase.givetool

import core.CoreModule
import core.repository.item.tool.ToolRepositoryImpl
import core.repository.player.PlayerRepository
import gamescreen.menu.MenuModule
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
    private val playerRepository: PlayerRepository by inject()
    private val bagRepository: BagRepository by inject()
    private val targetRepository: TargetRepository by inject()
    private val userRepository: UserRepository by inject()
    private val indexRepository: IndexRepository by inject()

    private val giveToolUseCase: GiveToolUseCase by inject()

    private val fromPlayer = 0
    private val fromPlayerStatus
        get() = playerRepository.getPlayers()[fromPlayer]

    private val toPlayer = 0
    private val toPlayerStatus
        get() = playerRepository.getPlayers()[toPlayer]

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MenuModule,
                CoreModule,
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
            // 渡す対象を設定
            val target = 0
            targetRepository.target = target

            //　対象の持ち物をいっぱいにする
            val player1 = playerRepository.getPlayers()[target]
            playerRepository.setStatus(
                id = target,
                status = player1.copy(
                    toolList = List(Constants.MAX_TOOL_NUM) {
                        ToolRepositoryImpl.HEAL_TOOL
                    }
                ),
            )

            // 渡す人を設定
            val from = 1
            userRepository.userId = from

            val fromPlayer = playerRepository.getPlayers()[from]
            playerRepository.setStatus(
                id = from,
                status = fromPlayer.copy(
                    toolList = List(Constants.MAX_TOOL_NUM) {
                        ToolRepositoryImpl.HEAL_TOOL
                    }
                )
            )

            indexRepository.index = 0

            val result = giveToolUseCase.invoke()

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
            // 一番上を渡す
            val result = moveToolFromPlayerToBag(
                index = 0,
                itemList = listOf(
                    0,
                    1,
                    2,
                )
            )

            assertTrue(
                result is GiveResult.OK
            )

            assertEquals(
                expected = listOf(
                    BagToolData(id = 0, num = 1)
                ),
                actual = bagRepository.getList()
            )

            assertEquals(
                expected = listOf(1, 2),
                actual = fromPlayerStatus.toolList
            )


            // 一番下を渡す
            val result2 = moveToolFromPlayerToBag(
                index = 2,
                itemList = listOf(
                    0,
                    1,
                    2,
                )
            )

            assertTrue(
                result2 is GiveResult.OK
            )

            assertEquals(
                expected = listOf(
                    BagToolData(id = 0, num = 1),
                    BagToolData(id = 2, num = 1),
                ),
                actual = bagRepository.getList()
            )

            assertEquals(
                expected = listOf(0, 1),
                actual = fromPlayerStatus.toolList
            )
        }
    }

    private suspend fun moveToolFromPlayerToBag(
        itemList: List<Int>,
        index: Int,
    ): GiveResult {
        // 渡される対象を袋に設定
        targetRepository.target = Constants.playerNum

        // 渡す人を設定
        userRepository.userId = fromPlayer

        // 渡す位置を設定
        indexRepository.index = index

        // 道具を設定
        playerRepository.setStatus(
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
            playerRepository.setStatus(
                id = toPlayer,
                status = toPlayerStatus.copy(
                    toolList = listOf()
                )
            )
            val result1 = moveFromBagToPlayer(
                itemList = listOf(
                    BagToolData(id = 0, num = 1),
                ),
                index = 0,
            )
            assertTrue(
                result1 is GiveResult.OK
            )
            assertEquals(
                expected = listOf(0),
                actual = toPlayerStatus.toolList,
            )
            assertEquals(
                expected = listOf(
                    BagToolData(
                        id = 0,
                        num = 0,
                    )
                ),
                actual = bagRepository.getList(),
            )

            val result2 = moveFromBagToPlayer(
                itemList = listOf(
                    BagToolData(id = 0, num = 1),
                    BagToolData(id = 2, num = 2),
                ),
                index = 1,
            )
            assertTrue(
                result2 is GiveResult.OK
            )
            assertEquals(
                expected = listOf(0, 2),
                actual = toPlayerStatus.toolList,
            )
            assertEquals(
                expected = listOf(
                    BagToolData(
                        id = 0,
                        num = 1,
                    ),
                    BagToolData(
                        id = 2,
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
