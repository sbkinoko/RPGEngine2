package menu.skill.usecase

import main.repository.skill.SkillRepository

class GetSkillExplainUseCaseImpl(
    private val skillRepository: SkillRepository,
) : GetSkillExplainUseCase {
    override fun invoke(id: Int): String {
        return skillRepository.getSkill(id).name + "\n" +
                "${id}番目のスキル"
    }
}
