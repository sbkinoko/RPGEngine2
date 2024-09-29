package core.usecase.checkcanuseskill

import core.domain.AbleType
import core.domain.Place
import core.domain.status.Status

interface CheckCanUseSkillUseCase {
    operator fun invoke(
        skillId: Int,
        status: Status,
        here: Place,
    ): AbleType
}
