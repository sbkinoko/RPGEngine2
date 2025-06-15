package gamescreen.battle.usecase.decideactionorder

import gamescreen.battle.domain.StatusWrapper

class DecideActionOrderUseCaseImpl : DecideActionOrderUseCase {
    // todo 同じ素早さの場合の挙動を考える
    override fun invoke(
        statusList: List<StatusWrapper>,
    ): List<StatusWrapper> {
        return statusList
            // 素早さ順でソート
            .sortedWith(speedComparator)
            // 降順にする
            .reversed()
    }

    private val speedComparator: Comparator<StatusWrapper> = compareBy {
        it.status.speed.value
    }
}
