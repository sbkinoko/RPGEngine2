package domain.common.status

class HP(
    value: Int,
    maxValue: Int,
) : Point(
    value = value,
    maxValue = maxValue,
) {
    companion object {
        val MIN_MAX_VALUE = 1
    }
}
