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
        }

    var maxPoint: Int = 10

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
