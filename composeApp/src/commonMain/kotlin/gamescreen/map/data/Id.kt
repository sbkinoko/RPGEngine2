package gamescreen.map.data

// TODO: enumつくる
const val ID_NON_LOOP = 0
const val ID_LOOP = 1

fun MapData.toId(): Int {
    return when (this) {
        is NonLoopMap -> ID_NON_LOOP
        is LoopMap -> ID_LOOP
        else -> throw NotImplementedError()
    }
}

fun Int.toMap(): MapData {
    return when (this) {
        ID_NON_LOOP -> NonLoopMap()
        else -> LoopMap()
    }
}
