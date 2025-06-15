package core.usecase.heal

import core.domain.status.StatusData
import core.domain.status.StatusDataTest
import core.domain.status.StatusType
import core.repository.statusdata.StatusDataRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MaxHealUseCaseImplTest {
    private val playerStatusRepository = object : StatusDataRepository<StatusType.Player> {
        private var statusList = MutableList(2) {
            StatusDataTest.TestPlayerStatusActive.run {
                copy(
                    hp = hp.set(
                        value = 0,
                    ),
                    mp = mp.set(
                        value = 0
                    )
                )
            }
        }
        override val statusDataFlow: StateFlow<List<StatusData<StatusType.Player>>>
            get() = throw NotImplementedError()

        override fun getStatusData(id: Int): StatusData<StatusType.Player> {
            return statusList[id]
        }

        override fun setStatusData(
            id: Int,
            statusData: StatusData<StatusType.Player>,
        ) {
            throw NotImplementedError()
        }

        override fun getStatusList(): List<StatusData<StatusType.Player>> {
            return statusList
        }

        override fun setStatusList(statusList: List<StatusData<StatusType.Player>>) {
            throw NotImplementedError()
        }
    }

    private lateinit var maxHealUseCase: MaxHealUseCase

    @BeforeTest
    fun beforeTest() {
        maxHealUseCase = MaxHealUseCaseImpl(
            playerStatusRepository = playerStatusRepository,
        )
    }

    @Test
    fun check() {
        runBlocking {
            maxHealUseCase.invoke()

            playerStatusRepository.getStatusList().map {
                it.apply {
                    assertEquals(
                        expected = hp.maxPoint,
                        actual = hp.point
                    )

                    assertEquals(
                        expected = mp.maxPoint,
                        actual = mp.point
                    )
                }
            }
        }
    }
}
