package core.usecase.item.usetool

interface UseToolUseCase {
    operator fun invoke(
        userId: Int,
        toolId: Int,
        index: Int,
        targetId: Int,
    )
}
