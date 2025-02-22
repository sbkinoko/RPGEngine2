package core.service

import core.domain.item.CostType
import core.domain.status.Status

class CheckCanUseServiceImpl : CheckCanUseService {
    override fun invoke(
        status: Status,
        costType: CostType,
    ): Boolean {
        TODO("Not yet implemented")
    }
}
