package gamescreen.map.usecase.event.cellevent

interface CellEventUseCase {
    /**
     * 対応するマスのイベントを発火
     */
    operator fun invoke(
        cellId: Int,
    )
}
