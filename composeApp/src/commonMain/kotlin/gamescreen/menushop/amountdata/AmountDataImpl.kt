package gamescreen.menushop.amountdata

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AmountDataImpl : AmountData {
    override var maxNum = 99

    override val num
        get() = amount2.value * 10 +
                amount1.value

    private val mutableAmount1 = MutableStateFlow(0)
    override val amount1 = mutableAmount1.asStateFlow()

    private val mutableAmount2 = MutableStateFlow(0)
    override val amount2 = mutableAmount2.asStateFlow()

    override fun incAmount1() {
        if (num == maxNum) {
            set(
                value = 0,
            )
            return
        }

        set(value = num + 1)
    }

    override fun decAmount1() {
        if (num == 0) {
            set(value = maxNum)
            return
        }

        set(value = num - 1)
    }

    override fun incAmount2() {
        if (num == maxNum) {
            set(value = 0)
            return
        }

        set(value = num + 10)
    }

    override fun decAmount2() {
        if (num == 0) {
            set(value = maxNum)
            return
        }

        set(value = num - 10)
    }

    override fun set(value: Int) {
        if (value < 0) {
            set(value = 0)
            return
        }

        if (value > maxNum) {
            set(value = maxNum)
            return
        }

        val num1 = value % 10
        val num2 = (value % 100 - num1) / 10

        mutableAmount1.value = num1
        mutableAmount2.value = num2
    }
}
