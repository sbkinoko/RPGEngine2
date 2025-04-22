package common.extension

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
