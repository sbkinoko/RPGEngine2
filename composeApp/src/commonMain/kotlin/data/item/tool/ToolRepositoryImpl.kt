package data.item.tool

import core.domain.Place
import core.domain.item.ItemKind
import core.domain.item.TargetType
import core.domain.item.tool.HealTool

class ToolRepositoryImpl : ToolRepository {
    override fun getItem(id: Int): ItemKind.Tool {
        return when (id) {
            HEAL_TOOL -> HealTool(
                id = id,
                name = "回復1",
                targetNum = 1,
                usablePlace = Place.BOTH,
                isReusable = false,
                isDisposable = true,
                healAmount = 10,
                targetType = TargetType.ACTIVE
            )

            HEAL_TOOL2 -> HealTool(
                id = id,
                name = "回復2",
                targetNum = 1,
                usablePlace = Place.BOTH,
                isReusable = false,
                isDisposable = true,
                healAmount = 10,
                targetType = TargetType.ACTIVE
            )

            else -> throw NotImplementedError()
        }
    }

    companion object {
        const val HEAL_TOOL = 0
        const val HEAL_TOOL2 = 1
    }
}
