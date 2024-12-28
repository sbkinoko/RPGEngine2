package gamescreen.battle.usecase.getExp

import core.repository.battlemonster.BattleMonsterRepository

class GetExpUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
) : GetExpUseCase {
    override fun invoke(): Int {

        return battleMonsterRepository
            .getMonsters()
            .sumOf {
                it.exp
            }
    }
}
