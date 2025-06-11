package gamescreen.battle.usecase.getmoney

import core.repository.battlemonster.BattleInfoRepository

class GetMoneyUseCaseImpl(
    private val battleInfoRepository: BattleInfoRepository,
) : GetMoneyUseCase {
    override fun invoke(): Int {
        return battleInfoRepository
            .getStatusList()
            .sumOf {
                it.money
            }
    }
}
