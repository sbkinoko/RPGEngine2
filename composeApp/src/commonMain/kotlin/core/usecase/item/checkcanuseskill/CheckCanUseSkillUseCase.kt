package core.usecase.item.checkcanuseskill

import core.domain.AbleType
import core.domain.Place
import core.domain.status.StatusData
import data.repository.monster.item.skill.SkillId

interface CheckCanUseSkillUseCase {
    operator fun invoke(
        skillId: SkillId,
        status: StatusData,
        here: Place,
    ): AbleType
}
