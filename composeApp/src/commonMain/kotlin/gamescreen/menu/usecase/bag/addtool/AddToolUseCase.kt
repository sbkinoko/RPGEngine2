package gamescreen.menu.usecase.bag.addtool

interface AddToolUseCase {
    operator fun invoke(
        toolId: Int,
        toolNum: Int,
    )
}
