package gamescreen.battle.usecase.decideactionorder

import gamescreen.battle.domain.OrderData

interface DecideActionOrderUseCase {
    operator fun invoke(
        statusList: List<OrderData>,
    ): List<Int>
}
