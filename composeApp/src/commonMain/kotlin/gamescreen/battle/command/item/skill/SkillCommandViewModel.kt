package gamescreen.battle.command.item.skill

import core.domain.AbleType
import core.domain.Const
import core.domain.Place
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCase
import data.item.skill.SkillId
import data.item.skill.SkillRepository
import gamescreen.battle.command.item.ItemCommandViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.SkillCommand
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject

class SkillCommandViewModel : ItemCommandViewModel<SkillId>() {
    override val itemRepository: SkillRepository by inject()

    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    override val itemList: List<SkillId>
        get() = playerStatusRepository.getStatus(playerId).skillList

    override val playerId: Int
        get() = (commandRepository.nowBattleCommandType as? SkillCommand)?.playerId
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

    override fun getLastSelectedItemId(): SkillId {
        return actionRepository.getAction(
            playerId = playerId
        ).skillId
    }

    override fun canUse(position: Int): Boolean {
        val skillId = itemList[position]
        val status = playerStatusRepository.getStatus(playerId)
        val ableType = checkCanUseSkillUseCase.invoke(
            skillId = skillId,
            status = status,
            here = Place.BATTLE,
        )
        return ableType == AbleType.Able
    }
}
