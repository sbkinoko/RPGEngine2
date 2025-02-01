package values.event

import data.item.tool.ToolId

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

sealed class BoxId(
    var hasItem: Boolean = true,
) {
    data object Box1 : BoxId()
    data object Box2 : BoxId()
}
