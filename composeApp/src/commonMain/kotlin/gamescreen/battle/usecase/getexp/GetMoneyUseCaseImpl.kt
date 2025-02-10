package gamescreen.battle.usecase.getexp

import core.repository.battlemonster.BattleInfoRepository

class GetExpUseCaseImpl(
    private val battleInfoRepository: BattleInfoRepository,
) : GetExpUseCase {
    override fun invoke(): Int {

        return battleInfoRepository
            .getMonsters()
            .sumOf {
                it.exp
            }
    }
}
