package core.usecase

import core.domain.AbleType
import core.domain.Place
import main.status.Status

interface CheckCanUseSkillUseCase {
    operator fun invoke(
        skillId: Int,
        status: Status,
        here: Place,
    ): AbleType
}
