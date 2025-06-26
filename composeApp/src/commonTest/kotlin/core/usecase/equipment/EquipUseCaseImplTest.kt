package core.usecase.equipment

import core.domain.item.item_.equipment.Equipment
import core.domain.item.item_.equipment.EquipmentData
import core.domain.item.item_.equipment.EquipmentType
import core.domain.status.IncData
import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusDataTest
import core.domain.status.StatusIncrease
import core.domain.status.StatusType
import core.domain.status.param.EXP
import core.domain.status.param.StatusParameter
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.item.skill.SkillId
import data.item.tool.ToolId
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class EquipUseCaseImplTest {
    val initAtk = 10

    private val statusDataRepository = object : StatusDataRepository<StatusType.Player> {
        override val statusDataFlow: StateFlow<List<StatusData<StatusType.Player>>>
            get() = throw NotImplementedError()

        private var statusDataList = MutableList(5) {
            StatusDataTest.TestPlayerStatusActive.copy(
                atk = StatusParameter(initAtk)
            )
        }

        override fun getStatusData(id: Int): StatusData<StatusType.Player> {
            return statusDataList[id]
        }

        override fun getStatusList(): List<StatusData<StatusType.Player>> {
            throw NotImplementedError()
        }

        override fun setStatusList(statusList: List<StatusData<StatusType.Player>>) {
            throw NotImplementedError()
        }

        override fun setStatusData(
            id: Int,
            statusData: StatusData<StatusType.Player>,
        ) {
            statusDataList[id] = statusData
        }
    }

    private val initialEquipment = EquipmentData()

    private val playerRepository = object : PlayerStatusRepository {
        override val playerStatusFlow: StateFlow<List<PlayerStatus>>
            get() = throw NotImplementedError()

        private var statusList = MutableList(5) {
            PlayerStatus(
                skillList = emptyList(),
                toolList = emptyList(),
                exp = EXP(EXP.type1),
                equipmentList = initialEquipment,
            )
        }

        override fun getTool(
            playerId: Int,
            index: Int,
        ): ToolId {
            throw NotImplementedError()
        }

        override fun getSkill(
            playerId: Int,
            index: Int,
        ): SkillId {
            throw NotImplementedError()
        }

        override fun getStatus(id: Int): PlayerStatus {
            return statusList[id]
        }

        override fun getStatusList(): List<PlayerStatus> {
            throw NotImplementedError()
        }

        override suspend fun setStatus(
            id: Int,
            status: PlayerStatus,
        ) {
            this.statusList[id] = status
        }
    }

    private val useCase: EquipUseCase = EquipUseCaseImpl(
        playerStatusRepository = playerRepository,
        statusDataRepository = statusDataRepository,
    )

    @BeforeTest
    fun beforeTest() {
        assertEquals(
            expected = initialEquipment,
            actual = playerRepository.getStatus(0).equipmentList,
        )

        assertEquals(
            expected = initAtk,
            actual = statusDataRepository.getStatusData(0).atk.value
        )
    }

    @Test
    fun setEquipment() {
        runBlocking {
            val upATK = 11
            val equipment = Equipment(
                type = EquipmentType.Weapon,
                statusList = StatusIncrease.empty.copy(
                    atk = IncData(upATK),
                )
            )
            val target = 0
            useCase.invoke(
                target = target,
                equipmentId = equipment,
            )

            assertEquals(
                expected = initAtk + upATK,
                actual = statusDataRepository
                    .getStatusData(target)
                    .atk
                    .value
            )

            assertEquals(
                expected = initialEquipment.copy(
                    weapon = equipment,
                ),
                actual = playerRepository
                    .getStatus(target)
                    .equipmentList
            )
        }
    }

    @Test
    fun setEquipment2() {
        runBlocking {
            val upATK = 11
            val equipment = Equipment(
                type = EquipmentType.Weapon,
                statusList = StatusIncrease.empty.copy(
                    atk = IncData(upATK),
                )
            )

            // もともと攻撃力5の装備をつけていた
            val target = 0
            val preWpAtk = 5
            val preStatus =
                playerRepository
                    .getStatus(target)
                    .copy(
                        equipmentList = initialEquipment.copy(
                            weapon = equipment.copy(
                                statusList = equipment.statusList.copy(
                                    atk = IncData(preWpAtk),
                                )
                            )
                        )
                    )
            playerRepository.setStatus(target, preStatus)


            useCase.invoke(
                target = target,
                equipmentId = equipment,
            )

            assertEquals(
                expected = initAtk + upATK - preWpAtk,
                actual = statusDataRepository
                    .getStatusData(target)
                    .atk
                    .value
            )

            assertEquals(
                expected = initialEquipment.copy(
                    weapon = equipment,
                ),
                actual = playerRepository
                    .getStatus(target)
                    .equipmentList
            )
        }
    }
}
