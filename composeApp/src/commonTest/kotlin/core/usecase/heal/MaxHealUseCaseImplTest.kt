package core.usecase.heal

import core.domain.status.StatusData
import core.domain.status.StatusDataTest
import core.repository.character.statusdata.StatusDataRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MaxHealUseCaseImplTest {
    private val playerStatusRepository = object : StatusDataRepository {
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
        override val statusDataFlow: StateFlow<List<StatusData>>
            get() = throw NotImplementedError()

        override fun getStatusData(id: Int): StatusData {
            return statusList[id]
        }

        override fun setStatusData(
            id: Int,
            statusData: StatusData,
        ) {
            statusList[id] = statusData
        }

        override fun getStatusList(): List<StatusData> {
            return statusList
        }

        override fun setStatusList(statusList: List<StatusData>) {
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
