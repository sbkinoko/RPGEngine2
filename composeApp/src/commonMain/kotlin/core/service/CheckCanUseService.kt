package core.service

import core.domain.item.CostType
import core.domain.status.StatusData

interface CheckCanUseService {
    operator fun invoke(
        status: StatusData,
        costType: CostType,
    ): Boolean
}
