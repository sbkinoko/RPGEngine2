package gamescreen.map.data

private const val ID_NON_LOOP = 0
private const val ID_LOOP = 1

enum class MapID(val intId: Int) {
    NON_LOOP(ID_NON_LOOP),
    LOOP(ID_LOOP),
    ;

    companion object {
        fun itoId(i: Int): MapID {
            entries.forEach {
                if (it.intId == i) {
                    return it
                }
            }

            throw RuntimeException()
        }
    }
}

fun MapData.toId(): MapID {
    return when (this) {
        is NonLoopMap -> MapID.NON_LOOP
        is LoopMap -> MapID.LOOP
        else -> throw NotImplementedError()
    }
}

fun MapID.toMap(): MapData {
    return when (this) {
        MapID.NON_LOOP -> NonLoopMap()
        MapID.LOOP -> LoopMap()
    }
}
