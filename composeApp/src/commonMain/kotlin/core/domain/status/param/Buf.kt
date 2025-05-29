package core.domain.status.param

data class Buf<T : BufType>(
    val value: Int,
    val rest: Int,
    val bufType: T,
) {
    fun reduceTurn(): Buf<T> = copy(
        rest = rest - 1,
    )
}
