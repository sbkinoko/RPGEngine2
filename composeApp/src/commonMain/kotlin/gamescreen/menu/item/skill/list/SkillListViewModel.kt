package gamescreen.menu.item.skill.list

import core.domain.AbleType
import core.domain.Place
import core.domain.item.Skill
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCase
import data.repository.item.skill.SkillId
import data.repository.item.skill.SkillRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.itemselect.ItemListViewModel
import gamescreen.menu.usecase.usetoolinmap.UseItemInMapUseCase
import org.koin.core.component.inject

class SkillListViewModel(
    useItemInMapUseCase: UseItemInMapUseCase,
) : ItemListViewModel<SkillId, Skill>(
    useItemInMapUseCase = useItemInMapUseCase,
) {
    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    override val itemRepository: SkillRepository by inject()

    override val boundedScreenType: MenuType
        get() = MenuType.SKILL_LST
    override val nextScreenType: MenuType
        get() = MenuType.SKILL_TARGET

    override val selectedItem: Skill
        get() = itemRepository.getItem(
            itemIdList[selectedFlowState.value]
        )

    override fun getPlayerItemIdListAt(id: Int): List<SkillId> {
        return playerStatusRepository.getStatus(id).skillList
    }

    override fun getAbleType(): AbleType {
        val skillId = itemIdList[selectCore.stateFlow.value]
        val status = statusDataRepository.getStatusData(userId)

        return checkCanUseSkillUseCase.invoke(
            skillId = skillId,
            status = status,
            here = Place.MAP,
        )
    }
}
