package gamescreen.battle.usecase.decideactionorder

import gamescreen.battle.domain.StatusWrapper

interface DecideActionOrderUseCase {
    operator fun invoke(
        statusList: List<StatusWrapper>,
    ): List<StatusWrapper>
}
