package core.usecase.item.checkcanuseskill

import core.domain.AbleType
import core.domain.Place
import core.domain.status.Status
import data.item.skill.SkillId
import data.item.skill.SkillRepository

class CheckCanUseSkillUseCaseImpl(
    private val skillRepository: SkillRepository,
) : CheckCanUseSkillUseCase {
    override fun invoke(
        skillId: SkillId,
        status: Status,
        here: Place,
    ): AbleType {
        val skill = skillRepository.getItem(skillId)

        if (Place.canUsePlace(
                here = here,
                usablePlace = skill.usablePlace,
            ).not()
        ) {
            // 使えない場所だった
            return AbleType.CANT_USE_BY_PLACE
        }

        // fixme　コストのタイプで判断して、使用可否を返却する
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
