package core.usecase.item.checkcanuseskill

import core.domain.AbleType
import core.domain.Place
import core.domain.item.UsableItem
import core.domain.status.StatusData
import core.service.CheckCanUseService
import data.item.skill.SkillId
import data.item.skill.SkillRepository

class CheckCanUseSkillUseCaseImpl(
    private val skillRepository: SkillRepository,
    private val checkCanUseService: CheckCanUseService,
) : CheckCanUseSkillUseCase {
    override fun invoke(
        skillId: SkillId,
        status: StatusData,
        here: Place,
    ): AbleType {
        val skill = skillRepository.getItem(skillId) as UsableItem

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
            checkCanUseService.invoke(
                status = status,
                costType = skill.costType
            )
        ) {
            AbleType.Able
        } else {
            AbleType.CANT_USE_BY_MP
        }
    }
}
