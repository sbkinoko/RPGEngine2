package data.status

class StatusRepositoryImpl : StatusRepository {
    override fun getStatus(id: Int, level: Int): StatusIncrease {
        return statusList[id].first()
    }

    private val statusList: List<List<StatusIncrease>> = List(3) {
        when (it) {
            0 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 10,
                )
            )

            1 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 111,
                )
            )

            2 -> listOf(
                StatusIncrease(
                    hp = 100,
                    mp = 100,
                )
            )

            else -> {
                throw IllegalStateException()
            }
        }
    }

}
