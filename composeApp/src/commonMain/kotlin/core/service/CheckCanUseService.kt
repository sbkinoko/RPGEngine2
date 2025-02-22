package core.service

import core.domain.item.CostType
import core.domain.status.Status

interface CheckCanUseService {
    operator fun invoke(
        status: Status,
        costType: CostType,
    ): Boolean
}
