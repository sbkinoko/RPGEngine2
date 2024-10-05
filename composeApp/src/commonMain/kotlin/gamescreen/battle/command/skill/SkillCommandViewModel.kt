package battle.command.skill

import battle.BattleChildViewModel
import battle.domain.ActionType
import battle.domain.BattleCommandType
import battle.domain.SelectAllyCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
import battle.repository.action.ActionRepository
import core.domain.AbleType
import core.domain.Const
import core.domain.Place
import core.domain.item.skill.AttackSkill
import core.domain.item.skill.HealSkill
import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerRepository
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import menu.domain.SelectManager
import org.koin.core.component.inject
import kotlin.math.max

class SkillCommandViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val playerRepository: PlayerRepository by inject()
    private val skillRepository: SkillRepository by inject()

    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    val skillList: List<Int>
        get() {
            return playerRepository.getStatus(playerId).skillList
        }

    val playerId: Int
        get() = (commandRepository.nowCommandType as? SkillCommand)?.playerId
            ?: Const.INITIAL_PLAYER


    private val selectedSkillId: Int
        get() = skillList[selectManager.selected]

    fun init() {
        // 最後に選ばれていたスキルを呼び出し
        val skillId = actionRepository.getAction(
            playerId = playerId
        ).skillId

        // skillIdを発見できない場合は先頭を返す
        //　それ以外はskillIdの場所を返す
        selectManager.selected = max(
            skillList.indexOf(skillId),
            Const.INITIAL_PLAYER,
        )
    }

    override fun selectable(): Boolean {
        return canUse(selectedSkillId)
    }

    fun getName(id: Int): String {
        return skillRepository.getSkill(id).name
    }

    fun canUse(id: Int): Boolean {
        val status = playerRepository.getStatus(playerId)
        val ableType = checkCanUseSkillUseCase.invoke(
            skillId = id,
            status = status,
            here = Place.BATTLE,
        )
        return ableType == AbleType.Able
    }

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is SkillCommand
    }

    override fun goNextImpl() {
        val skillId = selectedSkillId
        //　使えないので進まない
        if (canUse(skillId).not()) {
            return
        }

        actionRepository.setAction(
            actionType = ActionType.Skill,
            playerId = playerId,
            skillId = skillId,
        )
        when (skillRepository.getSkill(skillId)) {
            is AttackSkill -> {
                commandRepository.push(
                    SelectEnemyCommand(playerId),
                )
            }

            is HealSkill -> {
                commandRepository.push(
                    SelectAllyCommand(playerId),
                )
            }
        }
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = skillList.size,
    )
}
