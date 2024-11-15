package gamescreen.menu.item.skill.user

import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerStatusRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import org.koin.core.component.inject
import values.Constants

class SkillUserViewModel : ItemUserViewModel() {
    val repository: PlayerStatusRepository by inject()

    override val itemRepository: SkillRepository by inject()
    override val canBack: Boolean
        get() = true
    override val boundedScreenType: MenuType
        get() = MenuType.SKILL_USER
    override val nextScreenType: MenuType
        get() = MenuType.SKILL_LST

    override val playerNum: Int
        get() = Constants.playerNum

    override var selectManager: SelectManager =
        SelectManager(
            width = 1,
            itemNum = playerNum,
        )

    override fun getPlayerNameAt(id: Int): String {
        return playerStatusRepository.getStatus(id).name
    }

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return repository.getStatus(id).skillList
    }
}
