package domain.common.status

class Status {
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
            return true
        }
}
