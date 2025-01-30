package gamescreen.battle.usecase.decideactionorder

import gamescreen.battle.domain.OrderData

class DecideActionOrderUseCaseImpl(
) : DecideActionOrderUseCase {
    override fun invoke(
        statusList: List<OrderData>,
    ): List<Int> {
        return statusList
            // 素早さ順でソート
            .sortedWith(speedComparator)
            // 降順にする
            .reversed()
            // idだけ取り出す
            .map {
                it.id
            }
    }

    private val speedComparator: Comparator<OrderData> = compareBy {
        it.status.speed
    }
}
