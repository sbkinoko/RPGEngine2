package gamescreen.battle.usecase.getdroptool

import core.repository.item.tool.ToolRepositoryImpl
import kotlin.random.Random

class GetDropToolUseCaseImpl : GetDropToolUseCase {
    override fun invoke(): Int? {

        // fixme 敵の種類によってドロップ内容を変更する
        return if (Random.nextInt(2) % 2 == 0) {
            ToolRepositoryImpl.HEAL_TOOL
        } else {
            null
        }
    }
}
