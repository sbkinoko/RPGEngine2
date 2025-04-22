package common.extension

class MapCounter<T> {
    private val mutableMap: MutableMap<T, Int> = mutableMapOf()

    fun inc(key: T) {
        mutableMap[key] = mutableMap.getOrElse(key) { 0 } + 1
    }

    val ranking
        get() = mutableMap.sortByCount()
}

fun <T> MutableMap<T, Int>.sortByCount(): List<T> {
    val sortMap: MutableList<Pair<Int, T>> = mutableListOf()

    this.forEach {
        sortMap += it.value to it.key
    }

    sortMap.sortedBy { it.first }

    return sortMap.sortedBy { it.first }.reversed().map {
        it.second
    }
}
