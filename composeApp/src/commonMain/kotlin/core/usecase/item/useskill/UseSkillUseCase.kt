package core.usecase.item.useskill

interface UseSkillUseCase {
    suspend operator fun invoke(
        userId: Int,
        targetId: Int,
        index: Int,
    )
}
