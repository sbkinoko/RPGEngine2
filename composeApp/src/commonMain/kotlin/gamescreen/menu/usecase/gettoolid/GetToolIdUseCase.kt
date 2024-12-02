package gamescreen.menu.usecase.gettoolid

interface GetToolIdUseCase {
    operator fun invoke(
        userId: Int,
        index: Int,
    ): Int
}
