package gamescreen.battle.usecase.getdroptool

import core.repository.memory.character.battlemonster.BattleInfoRepository
import data.repository.item.tool.ToolId
import kotlin.random.Random

class GetDropToolUseCaseImpl(
    private val battleInfoRepository: BattleInfoRepository,
) : GetDropToolUseCase {

    override fun invoke(): List<ToolId> {
        // ドロップアイテムの情報を格納するリスト
        val dropList: MutableList<ToolId> = mutableListOf()

        battleInfoRepository.getStatusList().map { monster ->
            monster.dropInfoList.map {
                val rnd = Random.nextInt(100)
                if (rnd < it.probability) {
                    dropList.add(it.toolId)
                }
            }
        }

        return dropList
    }
}
