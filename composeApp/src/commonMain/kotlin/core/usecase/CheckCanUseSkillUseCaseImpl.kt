package core.usecase

import core.domain.AbleType
import core.domain.Place
import main.repository.skill.SkillRepository
import main.status.Status

class CheckCanUseSkillUseCaseImpl(
    private val skillRepository: SkillRepository,
) : CheckCanUseSkillUseCase {
    override fun invoke(
        skillId: Int,
        status: Status,
        here: Place,
    ): AbleType {
        val skill = skillRepository.getSkill(skillId)

        if (Place.canUsePlace(
                here = here,
                usablePlace = skill.usablePlace,
            ).not()
        ) {
            // 使えない場所だった
            return AbleType.CANT_USE_BY_PLACE
        }

        return if (
            skill.canUse(
                status.mp.value
            )
        ) {
            AbleType.Able
        } else {
            AbleType.CANT_USE_BY_MP
        }
    }
}
