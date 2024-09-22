package menu.skill.usecase

interface GetSkillExplainUseCase {
    operator fun invoke(id: Int): String
}
