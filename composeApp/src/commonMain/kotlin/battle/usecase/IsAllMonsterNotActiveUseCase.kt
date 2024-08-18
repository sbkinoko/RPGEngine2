package battle.usecase

import battle.repository.battlemonster.BattleMonsterRepository
import org.koin.core.component.KoinComponent

class IsAllMonsterNotActiveUseCase(
    private val battleMonsterRepository: BattleMonsterRepository
) : KoinComponent {
    /**
     * 敵が全滅したかどうかをチェック
     */
    operator fun invoke(): Boolean {
        return !battleMonsterRepository.getMonsters().any {
            it.isActive
        }
    }
}
