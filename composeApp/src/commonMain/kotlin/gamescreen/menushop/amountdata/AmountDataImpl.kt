package gamescreen.menushop.amountdata

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AmountDataImpl : AmountData {
    override var maxNum = 99

    val num
        get() = amount3.value * 100 +
                amount2.value * 10 +
                amount1.value

    private val mutableAmount1 = MutableStateFlow(0)
    override val amount1 = mutableAmount1.asStateFlow()

    private val mutableAmount2 = MutableStateFlow(0)
    override val amount2 = mutableAmount2.asStateFlow()

    private val mutableAmount3 = MutableStateFlow(0)
    override val amount3 = mutableAmount3.asStateFlow()


    override fun incAmount1() {
        if (num == maxNum) {
            set0()
            return
        }

        if (amount1.value == 9) {
            mutableAmount1.value = 0
            incAmount2()
        } else {
            mutableAmount1.value++
        }
        correctValue()
    }

    override fun incAmount2() {
        if (num == maxNum) {
            set0()
            return
        }

        if (amount2.value == 9) {
            mutableAmount2.value = 0
            incAmount3()
        } else {
            mutableAmount2.value++
        }
        correctValue()
    }

    override fun incAmount3() {
        mutableAmount3.value++
        correctValue()
    }

    //数が最大値を超えていたら最大にする
    private fun correctValue() {
        if (num > maxNum) {
            val num1 = maxNum % 10
            val num2 = (maxNum % 100 - num1) / 10
            val num3 = 0
            mutableAmount1.value = num1
            mutableAmount2.value = num2
            mutableAmount3.value = num3
        }
    }

    private fun set0() {
        mutableAmount1.value = 0
        mutableAmount2.value = 0
        mutableAmount3.value = 0
    }
}
