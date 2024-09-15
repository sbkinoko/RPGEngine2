package common.menu

class PairedList<T> {
    fun toPairedList(list: List<T>): List<Pair<T, T?>> {
        val pairedList: MutableList<Pair<T, T?>> = mutableListOf()
        for (cnt: Int in list.indices step 2) {
            if (cnt + 1 < list.size) {
                //　次の項目とセットで追加
                pairedList.add(Pair(list[cnt], list[cnt + 1]))
            } else {
                // もう項目がないのでnullを入れる
                pairedList.add(Pair(list[cnt], null))
            }
        }
        return pairedList.toList()
    }
}
