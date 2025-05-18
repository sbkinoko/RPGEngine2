package core.domain.status.param

abstract class StatusPoint<T : StatusPoint<T>>(
    open val data: StatusPointData,
) {
    val value
        get() = data.point

    val max
        get() = data.maxPoint


    // fixme abstractじゃなくする方法を探す
    abstract fun incMax(
        amount: Int,
    ): T

    abstract fun inc(amount: Int): T

    abstract fun dec(amount: Int): T

    abstract fun set(
        maxValue: Int = data.maxPoint,
        value: Int = data.point,
    ): T
}
