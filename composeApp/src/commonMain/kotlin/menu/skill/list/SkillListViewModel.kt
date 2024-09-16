package menu.skill.list

import common.Timer
import common.values.playerNum
import controller.domain.StickPosition
import main.repository.player.PlayerRepository
import main.repository.skill.SkillRepository
import menu.MenuChildViewModel
import menu.domain.MenuType
import menu.domain.SelectManager
import menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SkillListViewModel : MenuChildViewModel(),
    KoinComponent {
    val repository: PlayerRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val menuStateRepository: MenuStateRepository by inject()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )
    override val canBack: Boolean
        get() = true

    override var timer: Timer = Timer(200)

    private var _userId = 0
    var userId: Int
        get() {
            return _userId
        }
        set(value) {
            _userId = value
            loadSkill(value)
        }

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == MenuType.SKILL_LST
    }

    override fun goNextImpl() {
        TODO("Not yet implemented")
    }

    override fun moveStick(stickPosition: StickPosition) {
        super.moveStick(stickPosition)
        selectManager.selected
    }

    private fun loadSkill(userId: Int) {
        selectManager = SelectManager(
            width = 1,
            itemNum = repository.getStatus(userId).skillList.size,
        )
        selectManager.selected = 0
    }

    fun getExplainAt(id: Int): String {
        val skillId = repository.getStatus(userId).skillList[id]
        val explain = skillRepository.getSkill(skillId).name
        return explain
    }

    override fun pressB() {
        menuStateRepository.pop()
    }
}
