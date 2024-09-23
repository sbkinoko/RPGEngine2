package menu.skill.usecase.getskillexplain

interface GetSkillExplainUseCase {
    operator fun invoke(id: Int): String
}
