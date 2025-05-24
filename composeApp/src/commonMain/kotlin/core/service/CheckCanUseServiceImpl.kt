package core.service

import core.domain.item.CostType
import core.domain.status.Character

class CheckCanUseServiceImpl : CheckCanUseService {
    override fun invoke(
        status: Character,
        costType: CostType,
    ): Boolean {
        return when (costType) {
            is CostType.MP -> {
                // 現在のMPがコストのMPより多ければ使用可能
                status.statusData.mp.point >= costType.needMP
            }

            // ただの道具ならいつでも使える
            CostType.NotConsume,
            CostType.Consume,
                -> true
        }
    }
}
