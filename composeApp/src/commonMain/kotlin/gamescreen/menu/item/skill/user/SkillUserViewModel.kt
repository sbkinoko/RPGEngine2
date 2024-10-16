package gamescreen.menu.item.skill.user

import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import org.koin.core.component.inject

class SkillUserViewModel : ItemUserViewModel() {
    val repository: PlayerRepository by inject()

    override val itemRepository: SkillRepository by inject()
    override val canBack: Boolean
        get() = true
    override val boundedScreenType: MenuType
        get() = MenuType.SKILL_USER
    override val nextScreenType: MenuType
        get() = MenuType.SKILL_LST

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return repository.getStatus(id).skillList
    }
}
