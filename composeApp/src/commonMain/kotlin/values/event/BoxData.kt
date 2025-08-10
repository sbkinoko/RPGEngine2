package values.event

import data.repository.item.tool.ToolId

class BoxData {
    companion object {
        fun getItem(
            id: BoxId,
        ): ToolId {
            return when (id) {
                BoxId.Box1 -> ToolId.HEAL1
                BoxId.Box2 -> ToolId.HEAL2
            }
        }
    }
}

enum class BoxId {
    Box1,
    Box2,
}
