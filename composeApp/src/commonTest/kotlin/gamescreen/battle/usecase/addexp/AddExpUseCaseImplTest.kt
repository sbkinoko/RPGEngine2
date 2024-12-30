package gamescreen.battle.usecase.addexp

import core.domain.status.PlayerStatus
import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
import core.repository.player.PlayerStatusRepository
import core.repository.player.PlayerStatusRepositoryImpl
import data.status.StatusIncrease
import data.status.StatusRepositoryAbstract
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import values.Constants
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AddExpUseCaseImplTest {

    private val lv1Hp = 10
    private val lv1Mp = 11

    private val lv2Hp = 20
    private val lv2Mp = 21

    private val name = "test"

    private lateinit var playerStatusRepository: PlayerStatusRepository
    private lateinit var addExpUseCase: AddExpUseCase

    @BeforeTest
    fun beforeTest() {
        Constants.playerNum = 1


        playerStatusRepository = PlayerStatusRepositoryImpl(
            statusRepository = statusRepository,
        )

        addExpUseCase = AddExpUseCaseImpl(
            playerStatusRepository = playerStatusRepository,
            statusRepository = statusRepository,
        )
    }

    private val statusRepository = object : StatusRepositoryAbstract() {
        override val statusUpList: List<List<StatusIncrease>>
            get() = listOf(
                listOf(
                    StatusIncrease(
                        hp = lv1Hp,
                        mp = lv1Mp,
                    ),
                    StatusIncrease(
                        hp = lv2Hp,
                        mp = lv2Mp,
                    ),
                ),
            )
        override val statusBaseList: List<PlayerStatus>
            get() = listOf(
                PlayerStatus(
                    name = name,
                    hp = HP(0),
                    mp = MP(0),
                    toolList = listOf(),
                    skillList = listOf(),
                    exp = EXP(
                        listOf(
                            1,
                        )
                    )
                )
            )
    }


    @Test
    fun notLvUp() {

        runBlocking {
            val result = addExpUseCase.invoke(
                exp = 0,
            )

            assertTrue {
                result.isEmpty()
            }
        }
    }

    @Test
    fun lvUp() {
        runBlocking {
            val result = addExpUseCase(
                exp = 1,
            )

            delay(50)

            assertTrue {
                result.isNotEmpty()
            }

            assertEquals(
                expected = name,
                actual = result.first()
            )

            playerStatusRepository.getStatus(0).apply {
                assertEquals(
                    expected = lv1Hp,
                    actual = hp.value,
                )

                assertEquals(
                    expected = lv1Hp + lv2Hp,
                    actual = hp.maxValue,
                )

                assertEquals(
                    expected = lv1Mp,
                    actual = mp.value,
                )

                assertEquals(
                    expected = lv1Mp + lv2Mp,
                    actual = mp.maxValue,
                )
            }
        }
    }

    //fixme 複数人がレベルアップした場合のテストを作る
}
