package gamescreen.map.data

import data.item.tool.ToolRepositoryImpl

class BoxData {
    companion object {
        fun getItem(
            id: BoxId,
        ): Int {
            return when (id) {
                BoxId.Box1 -> ToolRepositoryImpl.HEAL_TOOL
                BoxId.Box2 -> ToolRepositoryImpl.HEAL_TOOL2
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
