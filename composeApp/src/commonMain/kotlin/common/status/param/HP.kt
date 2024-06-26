package common.status.param

data class HP(
    var maxValue: Int,
    var value: Int = maxValue,
) : Point() {
    override var maxPoint = 10
        set(value) {
            field = if (value < MIN_MAX_VALUE) {
                MIN_MAX_VALUE
            } else {
                value
            }

            if (maxPoint < point) {
                point = maxPoint
            }
        }

    init {
        point = value
        maxPoint = maxValue
    }

    companion object {
        const val MIN_MAX_VALUE = 1
    }
}
