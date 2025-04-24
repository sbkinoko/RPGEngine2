package gamescreen.menu.item.skill.user

import core.repository.player.PlayerStatusRepository
import data.item.skill.SkillId
import data.item.skill.SkillRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import org.koin.core.component.inject
import values.Constants

class SkillUserViewModel : ItemUserViewModel<SkillId>() {
    val repository: PlayerStatusRepository by inject()

    override val itemRepository: SkillRepository by inject()
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
        return playerStatusRepository.getStatus(id).statusData.name
    }

    override fun getPlayerItemIdListAt(id: Int): List<SkillId> {
        return repository.getStatus(id).skillList
    }
}
