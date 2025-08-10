package gamescreen.battle.usecase.getdroptool

import data.repository.item.tool.ToolId

interface GetDropToolUseCase {

    /**
     * 戦闘終了時のドロップアイテムを手に入れる処理
     */
    operator fun invoke(): List<ToolId>
}
