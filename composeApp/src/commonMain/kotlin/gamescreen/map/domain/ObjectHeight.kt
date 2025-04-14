package gamescreen.map.domain

sealed class ObjectHeight(
    open val height: Int,
) : Comparable<ObjectHeight> {
    data object None : ObjectHeight(height = 0)

    data class Water(
        override val height: Int,
    ) : ObjectHeight(height)

    data class Ground(
        override val height: Int,
    ) : ObjectHeight(height)

    override fun compareTo(other: ObjectHeight): Int {
        if (this.toInt() - other.toInt() != 0) {
            return this.toInt() - other.toInt()
        }

        return this.height - other.height
    }

    private fun toInt(): Int {
        return when (this) {
            None -> 3
            is Ground -> 2
            is Water -> 1
        }
    }
}
