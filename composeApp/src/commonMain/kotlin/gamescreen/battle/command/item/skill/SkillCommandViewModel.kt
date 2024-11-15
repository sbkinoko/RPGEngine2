package gamescreen.battle.command.item.skill

import core.domain.AbleType
import core.domain.Const
import core.domain.Place
import core.repository.item.skill.SkillRepository
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import gamescreen.battle.command.item.ItemCommandViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.SkillCommand
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject

class SkillCommandViewModel : ItemCommandViewModel() {
    override val itemRepository: SkillRepository by inject()

    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()


    override val itemList: List<Int>
        get() = playerStatusRepository.getStatus(playerId).skillList

    override val playerId: Int
        get() = (commandRepository.nowCommandType as? SkillCommand)?.playerId
            ?: Const.INITIAL_PLAYER

    override val actionType: ActionType
        get() = ActionType.Skill

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = itemList.size,
    )

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is SkillCommand
    }

    override fun getLastSelectedItemId(): Int {
        return actionRepository.getAction(
            playerId = playerId
        ).skillId
    }

    override fun canUse(id: Int): Boolean {
        val status = playerStatusRepository.getStatus(playerId)
        val ableType = checkCanUseSkillUseCase.invoke(
            skillId = id,
            status = status,
            here = Place.BATTLE,
        )
        return ableType == AbleType.Able
    }
}
