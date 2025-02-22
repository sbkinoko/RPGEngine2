package core.service

import core.domain.item.CostType
import core.domain.status.Status

class CheckCanUseServiceImpl : CheckCanUseService {
    override fun invoke(
        status: Status,
        costType: CostType,
    ): Boolean {
        return when (costType) {
            is CostType.MP -> {
                // 現在のMPがコストのMPより多ければ使用可能
                status.mp.value >= costType.needMP
            }

            // ただの道具ならいつでも使える
            CostType.NotConsume,
            CostType.Consume,
            -> true
        }
    }
}
