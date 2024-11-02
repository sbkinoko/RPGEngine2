package gamescreen.menu.usecase.bag.dectool

interface DecToolUseCase {
    operator fun invoke(
        itemId: Int,
        itemNum: Int,
    )
}
