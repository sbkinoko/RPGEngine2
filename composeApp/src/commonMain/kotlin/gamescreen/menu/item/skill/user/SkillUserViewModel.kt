package gamescreen.menu.item.skill.user

import core.domain.item.Skill
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.repository.item.skill.SkillId
import data.repository.item.skill.SkillRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import org.koin.core.component.inject
import values.Constants

class SkillUserViewModel(
    private val statusDataRepository: StatusDataRepository,
) : ItemUserViewModel<SkillId, Skill>() {
    val repository: PlayerStatusRepository by inject()

    override val itemRepository: SkillRepository by inject()
    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = playerNum,
        )
    )

    override val boundedScreenType: MenuType
        get() = MenuType.SKILL_USER
    override val nextScreenType: MenuType
        get() = MenuType.SKILL_LST

    override val playerNum: Int
        get() = Constants.playerNum

    override fun getPlayerNameAt(id: Int): String {
        return statusDataRepository.getStatusData(id).name
    }

    override fun getPlayerItemIdListAt(id: Int): List<SkillId> {
        return repository.getStatus(id).skillList
    }
}
