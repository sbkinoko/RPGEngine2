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

const val NONE = 0
const val Heal1 = 1
const val Heal2 = 2
const val FLY = 3
