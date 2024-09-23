package core.domain.status.param

data class MP(
    var maxValue: Int,
    var value: Int = maxValue,
) : Point() {

    override var maxPoint = maxValue
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
        maxPoint = maxValue
        point = value
    }

    companion object {
        const val MIN_MAX_VALUE = 0
    }
}
