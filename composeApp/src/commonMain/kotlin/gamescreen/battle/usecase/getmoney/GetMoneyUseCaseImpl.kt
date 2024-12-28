package gamescreen.battle.usecase.getmoney

import core.repository.battlemonster.BattleMonsterRepository

class GetMoneyUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
) : GetMoneyUseCase {
    override fun invoke(): Int {
        return battleMonsterRepository
            .getMonsters()
            .sumOf {
                it.money
            }
    }
}
