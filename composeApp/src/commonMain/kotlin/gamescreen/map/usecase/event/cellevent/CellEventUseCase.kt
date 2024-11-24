package gamescreen.map.usecase.event.cellevent

interface CellEventUseCase {
    operator fun invoke(
        cellId: Int,
    )
}
