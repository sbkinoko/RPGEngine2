package gamescreen.map.data

val ID_NON_LOOP = 0
val ID_LOOP = 1

fun MapData.toId(): Int {
    return when (this) {
        NonLoopMap() -> ID_NON_LOOP
        else -> ID_LOOP
    }
}

fun Int.toMap(): MapData {
    return when (this) {
        ID_NON_LOOP -> NonLoopMap()
        else -> LoopMap()
    }
}
