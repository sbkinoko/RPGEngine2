package domain.common.status

abstract class Point {
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

    abstract var maxPoint: Int

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
