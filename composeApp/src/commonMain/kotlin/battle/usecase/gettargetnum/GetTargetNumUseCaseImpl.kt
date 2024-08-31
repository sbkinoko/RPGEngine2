package battle.usecase.gettargetnum

import battle.domain.ActionType
import battle.repository.action.ActionRepository
import battle.repository.skill.SkillRepository

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
            }
        }
    }
}
