package domain.common.status

abstract class Point(
    maxValue: Int,
    value: Int,
) {
    var point: Int = 0
        set(value) {
            field = if (maxPoint <= value) {
                maxPoint
            } else {
                value
            }

            if (field < 0) {
                field = 0
            }
        }

    var maxPoint: Int = 10
        set(value) {
            field = value
            if (maxPoint < point) {
                point = maxPoint
            }
        }

    init {
        point = value
        maxPoint = maxValue
    }


//    fun incValue(){
//
//    }
//
//    fun decValue(){
//
//    }
//
//
//    fun setMaxValue(){
//
//    }
}
