package core.domain.status.param

sealed class BufType {
    data object Add : BufType()
}

data class Buf<T : BufType>(
    val value: Int,
    val rest: Int,
    val bufType: T,
) {
    fun reduceTurn(): Buf<T> = copy(
        rest = rest - 1,
    )
}
