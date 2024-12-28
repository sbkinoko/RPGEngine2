package gamescreen.battle.usecase.getdroptool

import core.repository.item.tool.ToolRepositoryImpl

class GetDropToolUseCaseImpl : GetDropToolUseCase {
    override fun invoke(): Int {
        // fixme 敵の種類によってドロップ内容を変更する
        // 確率にしたい
        return ToolRepositoryImpl.HEAL_TOOL
    }
}
