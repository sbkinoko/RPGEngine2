package gamescreen.map.data

import core.repository.item.tool.ToolRepositoryImpl

class BoxData {
    companion object {
        val list = mutableListOf(
            // 何も取得しないための0
            0,
            ToolRepositoryImpl.HEAL_TOOL2,
        )
    }
}
