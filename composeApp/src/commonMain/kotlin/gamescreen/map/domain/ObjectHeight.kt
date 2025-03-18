package gamescreen.map.domain

sealed class ObjectHeight(
    open val height: Int,
) {
    data object None : ObjectHeight(height = 0)

    data class Water(
        override val height: Int,
    ) : ObjectHeight(height)

    data class Ground(
        override val height: Int,
    ) : ObjectHeight(height)
}
