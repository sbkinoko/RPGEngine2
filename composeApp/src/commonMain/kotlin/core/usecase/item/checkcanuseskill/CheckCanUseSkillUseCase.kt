package core.usecase.item.checkcanuseskill

import core.domain.AbleType
import core.domain.Place
import core.domain.status.Character
import data.item.skill.SkillId

interface CheckCanUseSkillUseCase {
    operator fun invoke(
        skillId: SkillId,
        status: Character,
        here: Place,
    ): AbleType
}
