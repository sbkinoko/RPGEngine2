package gamescreen.battle.usecase.getexp

import core.repository.character.battlemonster.BattleInfoRepository

class GetExpUseCaseImpl(
    private val battleInfoRepository: BattleInfoRepository,
) : GetExpUseCase {
    override fun invoke(): Int {

        return battleInfoRepository
            .getStatusList()
            .sumOf {
                it.exp
            }
    }
}
