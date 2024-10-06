package core.repository.item.tool

import core.domain.Place
import core.domain.item.TargetType
import core.domain.item.tool.HealTool
import core.domain.item.tool.Tool

class ToolRepositoryImpl : ToolRepository {
    override fun getItem(id: Int): Tool {
        when (id) {
            HEAL_TOOL -> return HealTool(
                id = id,
                name = "回復",
                targetNum = 1,
                usablePlace = Place.BOTH,
                isReusable = true,
                isDisposable = true,
                healAmount = 10,
                targetType = TargetType.ACTIVE
            )

            else -> throw NotImplementedError()
        }
    }

    companion object {
        const val HEAL_TOOL = 0
    }
}
