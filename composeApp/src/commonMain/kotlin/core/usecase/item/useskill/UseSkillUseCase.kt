package core.usecase.item.useskill

interface UseSkillUseCase {
    operator fun invoke(
        userId: Int,
        targetId: Int,
        index: Int,
    )
}
