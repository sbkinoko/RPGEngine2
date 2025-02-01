package data.item.tool

import core.domain.Place
import core.domain.item.TargetType
import core.domain.item.Tool
import core.domain.item.tool.HealTool

class ToolRepositoryImpl : ToolRepository {
    override fun getItem(id: ToolId): Tool {
        return when (id) {
            ToolId.None -> throw RuntimeException()

            ToolId.HEAL1 -> HealTool(
                name = "回復1",
                targetNum = 1,
                usablePlace = Place.BOTH,
                isReusable = false,
                isDisposable = true,
                healAmount = 10,
                targetType = TargetType.ACTIVE
            )

            ToolId.HEAL2 -> HealTool(
                name = "回復2",
                targetNum = 1,
                usablePlace = Place.BOTH,
                isReusable = false,
                isDisposable = true,
                healAmount = 10,
                targetType = TargetType.ACTIVE
            )
        }
    }
}
