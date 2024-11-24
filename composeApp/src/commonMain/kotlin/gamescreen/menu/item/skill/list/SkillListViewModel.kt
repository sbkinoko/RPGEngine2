package gamescreen.menu.item.skill.list

import core.domain.AbleType
import core.domain.Place
import core.repository.item.skill.SkillRepository
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCase
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.itemselect.ItemListViewModel
import org.koin.core.component.inject

class SkillListViewModel : ItemListViewModel() {
    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    override val itemRepository: SkillRepository by inject()

    override val boundedScreenType: MenuType
        get() = MenuType.SKILL_LST
    override val nextScreenType: MenuType
        get() = MenuType.SKILL_TARGET

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return playerStatusRepository.getStatus(id).skillList
    }

    override fun getAbleType(): AbleType {
        val skillId = itemList[selectManager.selected]
        val status = playerStatusRepository.getStatus(userId)

        return checkCanUseSkillUseCase.invoke(
            skillId = skillId,
            status = status,
            here = Place.MAP,
        )
    }
}
