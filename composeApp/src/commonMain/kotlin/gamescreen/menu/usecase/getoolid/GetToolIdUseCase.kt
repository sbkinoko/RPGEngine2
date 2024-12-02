package gamescreen.menu.usecase.getoolid

interface GetToolIdUseCase {
    operator fun invoke(
        userId: Int,
        index: Int,
    ): Int
}
