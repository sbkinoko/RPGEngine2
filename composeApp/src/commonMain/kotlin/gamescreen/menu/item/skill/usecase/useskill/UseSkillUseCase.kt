package gamescreen.menu.item.skill.usecase.useskill

interface UseSkillUseCase {
    operator fun invoke(
        userId: Int,
        targetId: Int,
        index: Int,
    )
}
