package data.status

interface StatusRepository {

    fun getStatus(
        id: Int,
        level: Int,
    ): StatusIncrease

}
