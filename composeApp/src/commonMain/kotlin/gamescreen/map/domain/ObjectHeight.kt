package gamescreen.map.domain

import gamescreen.map.domain.ObjectHeight.None.height

private const val NONE = 0
private const val SKY = 3
private const val GROUND = 2
private const val WATER = 1

//fixme マジックナンバー消す
sealed class ObjectHeight(
    open val height: ObjectHeightDetail,
) : Comparable<ObjectHeight> {
    data object None : ObjectHeight(height = ObjectHeightDetail.Low)

    data class Water(
        override val height: ObjectHeightDetail,
    ) : ObjectHeight(height)

    data class Ground(
        override val height: ObjectHeightDetail,
    ) : ObjectHeight(height)

    data class Sky(
        val objectHeightDetail: ObjectHeightDetail,
    ) : ObjectHeight(height)

    override fun compareTo(other: ObjectHeight): Int {
        if (this.toInt() - other.toInt() != 0) {
            return this.toInt() - other.toInt()
        }

        return this.height.num - other.height.num
    }

    fun toInt(): Int {
        return when (this) {
            None -> NONE
            is Sky -> SKY
            is Ground -> GROUND
            is Water -> WATER
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
                SKY -> Sky(objectHeightDetail)

                GROUND -> Ground(objectHeightDetail)

                WATER -> Water(objectHeightDetail)

                else -> None
            }
        }
    }
}
