package core.domain.status

class StatusIncreaseTest {

    companion object {
        const val TEST_LV1_HP = 10
        const val TEST_LV1_MP = 11

        const val TEST_LV2_HP = 20
        const val TEST_LV2_MP = 21


        const val TEST_LV1_SPEED = 1
        const val TEST_LV2_SPEED = 2

        val testStatusUpList: List<StatusIncrease>
            get() = listOf(
                StatusIncrease(
                    hp = TEST_LV1_HP,
                    mp = TEST_LV1_MP,
                    speed = TEST_LV1_SPEED,
                ),
                StatusIncrease(
                    hp = TEST_LV2_HP,
                    mp = TEST_LV2_MP,
                    speed = TEST_LV2_SPEED,
                ),
            )
    }
}
