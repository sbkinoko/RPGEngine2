package battle.usecase

import battle.repository.battlemonster.BattleMonsterRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IsAllMonsterNotActiveUseCase : KoinComponent {
    private val battleMonsterRepository: BattleMonsterRepository by inject()

    /**
     * 敵が全滅したかどうかをチェック
     */
    operator fun invoke(): Boolean {
        return !battleMonsterRepository.getMonsters().any {
            it.isActive
        }
    }
}
