package gamescreen.menu.item.skill.target

import core.domain.AbleType
import core.domain.Place
import core.domain.item.Skill
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCase
import data.repository.item.skill.SkillId
import data.repository.item.skill.SkillRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.target.UsableTargetViewModel
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.usecase.usetoolinmap.UseItemInMapUseCase
import org.koin.core.component.inject

class SkillTargetViewModel(
    useItemInMapUseCase: UseItemInMapUseCase,
) : UsableTargetViewModel<SkillId, Skill>(
    useItemInMapUseCase = useItemInMapUseCase,
) {
    override val itemRepository: SkillRepository by inject()

    private val indexRepository: IndexRepository by inject()
    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    override val boundedMenuType: MenuType
        get() = MenuType.SKILL_TARGET

    override val itemId: SkillId
        get() = playerStatusRepository.getSkill(
            user,
            indexRepository.index,
        )

    override fun getAbleType(): AbleType {
        val userStatus = statusDataRepository.getStatusData(id = user)

        return checkCanUseSkillUseCase.invoke(
            status = userStatus,
            skillId = itemId,
            here = Place.MAP,
        )
    }
}
