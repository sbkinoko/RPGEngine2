package gamescreen.init

import core.domain.item.BagItemData
import core.repository.memory.bag.BagRepository
import core.repository.memory.character.player.PlayerCharacterRepository
import core.repository.memory.character.statusdata.StatusDataRepository
import core.repository.memory.money.MoneyRepository
import core.repository.storage.MoneyDBRepository
import core.repository.storage.player.PlayerDBRepository
import core.repository.storage.tool.ToolDBRepository
import data.repository.item.equipment.EquipmentId
import data.repository.item.tool.ToolId
import data.repository.status.StatusRepository
import values.Constants

class InitUseCaseImpl(
    private val equipmentBagRepository: BagRepository<EquipmentId>,
    private val toolBagRepository: BagRepository<ToolId>,
    private val statusDataRepository: StatusDataRepository,
    private val statusRepository: StatusRepository,
    private val moneyRepository: MoneyRepository,
    private val moneyDBRepository: MoneyDBRepository,

    private val playerDBRepository: PlayerDBRepository,

    private val toolDBRepository: ToolDBRepository,
    private val playerCharacterRepository: PlayerCharacterRepository,
) : InitUseCase {

    override suspend fun invoke() {

        initMoney()

        val expList = playerDBRepository.getPlayers()

        val statusList = List(Constants.playerNum) {
            statusRepository.getStatus(
                id = it,
                exp = expList[it],
            )
        }

        statusDataRepository.setStatusList(
            statusList.map { it.second }
        )

        playerCharacterRepository.setStatusList(
            statusList.map { it.first }
        )

        equipmentBagRepository.setData(
            data = BagItemData(
                id = EquipmentId.Sword,
                num = 100
            )
        )
        equipmentBagRepository.setData(
            data = BagItemData(
                id = EquipmentId.Shield,
                num = 100,
            )
        )
        equipmentBagRepository.setData(
            data = BagItemData(
                id = EquipmentId.MagicSword,
                num = 100
            )
        )

        toolBagRepository.setData(
            data = BagItemData(
                id = ToolId.HEAL1,
                num = 100,
            )
        )
        toolBagRepository.setData(
            data = BagItemData(
                id = ToolId.HEAL2,
                num = 100,
            )
        )
        toolBagRepository.setData(
            data = BagItemData(
                id = ToolId.Fly,
                num = 100,
            )
        )

        initTool()
    }

    private fun initMoney() {
        val amount = moneyDBRepository.get()
        moneyRepository.setMoney(amount)
    }

    private suspend fun initTool() {
        val tools = toolDBRepository.getTools()

        playerCharacterRepository.setStatusList(
            playerCharacterRepository.getStatusList().mapIndexed { idx, player ->
                player.copy(
                    toolList = tools[idx],
                )
            }
        )
    }
}
