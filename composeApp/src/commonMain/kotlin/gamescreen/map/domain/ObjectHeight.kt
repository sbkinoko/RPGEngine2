package gamescreen.map.domain

enum class ObjectHeightDetail(
    val num: Int,
) {
    Back(0),
    Normal(1),
    Front(2),
}



//fixme マジックナンバー消す
sealed class ObjectHeight(
    open val height: ObjectHeightDetail,
) : Comparable<ObjectHeight> {
    data object None : ObjectHeight(height = ObjectHeightDetail.Back)

    data class Water(
        override val height: ObjectHeightDetail,
    ) : ObjectHeight(height)

    data class Ground(
        override val height: ObjectHeightDetail,
    ) : ObjectHeight(height)

    override fun compareTo(other: ObjectHeight): Int {
        if (this.toInt() - other.toInt() != 0) {
            return this.toInt() - other.toInt()
        }

        return this.height.num - other.height.num
    }

    fun toInt(): Int {
        return when (this) {
            None -> 3
            is Ground -> 2
            is Water -> 1
        }
    }

    companion object {
        fun ObjectHeight(
            height: Int,
            heightDetail: Int,
        ): ObjectHeight {
            lateinit var objectHeightDetail: ObjectHeightDetail

            ObjectHeightDetail.entries.map {
                if (it.num == heightDetail) {
                    objectHeightDetail = it
                }
            }

            return when (height) {
                2 -> Ground(objectHeightDetail)

                1 -> Water(objectHeightDetail)

                else -> None
            }
        }
    }
}
