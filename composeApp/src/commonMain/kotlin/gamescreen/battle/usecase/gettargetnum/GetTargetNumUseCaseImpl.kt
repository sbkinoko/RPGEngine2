package gamescreen.battle.usecase.gettargetnum

import core.repository.skill.SkillRepository
import gamescreen.battle.domain.ActionType
import gamescreen.battle.repository.action.ActionRepository

class GetTargetNumUseCaseImpl(
    private val actionRepository: ActionRepository,
    private val skillRepository: SkillRepository
) : GetTargetNumUseCase {
    override fun invoke(playerId: Int): Int {
        return actionRepository.getAction(playerId).let {
            when (it.thisTurnAction) {
                ActionType.Normal -> 1
                ActionType.Skill -> {
                    skillRepository.getSkill(it.skillId!!).targetNum
                }

                ActionType.None -> throw RuntimeException()
            }
        }
    }
}
