package core.service

import core.domain.AbleType
import core.domain.item.CostType
import core.domain.status.StatusData

class CheckCanUseServiceImpl : CheckCanUseService {
    override fun invoke(
        status: StatusData,
        costType: CostType,
    ): AbleType {
        return when (costType) {
            is CostType.MP -> {
                // 現在のMPがコストのMPより多ければ使用可能
                return if (status.mp.point >= costType.needMP) {
                    AbleType.Able
                } else {
                    AbleType.CANT_USE_BY_MP
                }
            }

            // ただの道具ならいつでも使える
            CostType.NotConsume,
            CostType.Consume,
                -> AbleType.Able
        }
    }
}
