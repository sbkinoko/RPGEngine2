package core.service

import core.domain.item.CostType
import core.domain.status.Character

interface CheckCanUseService {
    operator fun invoke(
        status: Character,
        costType: CostType,
    ): Boolean
}
