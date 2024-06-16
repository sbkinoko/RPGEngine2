package domain.common.status

open class Status {
    lateinit var name: String

    val hp = HP(
        value = 10,
        maxValue = 100,
    )

    val mp = MP(
        value = 10,
        maxValue = 100,
    )

    val isActive: Boolean
        get() {
            return 0 < hp.point
        }
}
