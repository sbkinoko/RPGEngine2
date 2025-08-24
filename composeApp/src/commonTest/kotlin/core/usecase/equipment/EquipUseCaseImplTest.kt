package core.usecase.equipment

import core.domain.item.equipment.EquipmentData
import core.domain.item.equipment.EquipmentList
import core.domain.item.equipment.EquipmentType
import core.domain.status.IncData
import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusDataTest
import core.domain.status.StatusIncrease
import core.domain.status.param.EXP
import core.domain.status.param.StatusParameter
import data.repository.item.equipment.EquipmentId
import data.repository.item.equipment.EquipmentRepository
import data.repository.item.skill.SkillId
import data.repository.item.tool.ToolId
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class EquipUseCaseImplTest {
    val initAtk = 10

    val upATK1 = 10
    val upATK2 = 15

    private val statusDataRepository = object : core.repository.memory.character.statusdata.StatusDataRepository {
        override val statusDataFlow: StateFlow<List<StatusData>>
            get() = throw NotImplementedError()

        private var statusDataList = MutableList(5) {
            StatusDataTest.TestPlayerStatusActive.copy(
                atk = StatusParameter(initAtk)
            )
        }

        override fun getStatusData(id: Int): StatusData {
            return statusDataList[id]
        }

        override fun getStatusList(): List<StatusData> {
            throw NotImplementedError()
        }

        override fun setStatusList(statusList: List<StatusData>) {
            throw NotImplementedError()
        }

        override fun setStatusData(
            id: Int,
            statusData: StatusData,
        ) {
            statusDataList[id] = statusData
        }
    }

    private val initialEquipment = EquipmentList()

    private val playerRepository = object : core.repository.memory.character.player.PlayerCharacterRepository {
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

    private val equipmentRepository = object : EquipmentRepository {

        override fun getItem(id: EquipmentId): EquipmentData {
            return when (id) {
                EquipmentId.Sword -> EquipmentData(
                    name = "けん",
                    explain = "けん",
                    type = EquipmentType.Weapon,
                    statusList = StatusIncrease.empty.copy(
                        atk = IncData(upATK1),
                    )
                )

                EquipmentId.Shield,

                    -> EquipmentData(
                    name = "けん２",
                    explain = "けん2",
                    type = EquipmentType.Weapon,
                    statusList = StatusIncrease.empty.copy(
                        atk = IncData(upATK2),
                    )
                )

                EquipmentId.None,
                    -> EquipmentData(
                    name = "",
                    explain = "",
                    statusList = StatusIncrease.empty,
                    type = EquipmentType.Weapon,
                )

                else -> throw NotImplementedError()
            }
        }
    }

    private val useCase: EquipUseCase = EquipUseCaseImpl(
        playerStatusRepository = playerRepository,
        statusDataRepository = statusDataRepository,
        equipmentRepository = equipmentRepository,
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
            val equipment = EquipmentId.Sword

            val target = 0
            useCase.invoke(
                target = target,
                equipmentId = equipment,
            )

            assertEquals(
                expected = initAtk + upATK1,
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
            val equipment = EquipmentId.Shield

            // もともと攻撃力5の装備をつけていた
            val target = 0
            val preStatus =
                playerRepository
                    .getStatus(target)
                    .copy(
                        equipmentList = initialEquipment.copy(
                            weapon = EquipmentId.Sword,
                        )
                    )
            playerRepository.setStatus(target, preStatus)

            useCase.invoke(
                target = target,
                equipmentId = equipment,
            )

            assertEquals(
                expected = initAtk + upATK2 - upATK1,
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

    // todo 剣と盾をつけるとステータスが2回上がることを確認
}
