package core.domain.status.param

data class Buf<T : BufType>(
    val value: Int,
    val rest: Int,
    val bufType: T,
) {
    fun reduceTurn(): Buf<T>? {
        if (rest == 1) {
            return null
        }

        return copy(
            rest = rest - 1,
        )
    }
}
