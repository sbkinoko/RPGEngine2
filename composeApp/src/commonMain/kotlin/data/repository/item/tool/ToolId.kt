package data.repository.item.tool

import data.repository.item.ItemId

enum class ToolId(val id: Int) : ItemId {
    None(NONE),
    HEAL1(Heal1),
    HEAL2(Heal2),
    Fly(FLY),
    ;

    companion object {
        fun getEnum(id: Int): ToolId {
            entries.forEach { toolId ->
                if (toolId.id == id) {
                    return toolId
                }
            }

            throw RuntimeException()
        }
    }
}

private const val NONE = 0
private const val Heal1 = 1
private const val Heal2 = 2
private const val FLY = 3
