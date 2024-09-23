package core.domain.status.param

abstract class Point {
    var point: Int = 0
        set(value) {
            // 最大値を超えないように設定
            field = if (maxPoint <= value) {
                maxPoint
            } else {
                value
            }

            // 最小値は0
            if (field < 0) {
                field = 0
            }
        }

    abstract var maxPoint: Int

}
