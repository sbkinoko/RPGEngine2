package gamescreen.menushop.domain.amountdata

import common.layout.spinnbutton.SpinButtonData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AmountDataImpl : AmountData {
    override var maxNum = 99

    override val num
        get() = mutableAmount2.value * 10 +
                mutableAmount1.value

    private val mutableAmount1 = MutableStateFlow(0)
    private val mutableAmount2 = MutableStateFlow(0)

    override val buttonData1 = object : SpinButtonData<Int> {
        override val dataFlow: StateFlow<Int>
            get() = mutableAmount1.asStateFlow()

        override fun onClickAdd() {
            if (num == maxNum) {
                set(
                    value = 0,
                )
                return
            }

            set(value = num + 1)
        }

        override fun onClickDec() {
            if (num == 0) {
                set(value = maxNum)
                return
            }

            set(value = num - 1)
        }
    }

    override val buttonData10 = object : SpinButtonData<Int> {
        override val dataFlow: StateFlow<Int>
            get() = mutableAmount2.asStateFlow()

        override fun onClickAdd() {
            if (num == maxNum) {
                set(value = 0)
                return
            }

            set(value = num + 10)
        }

        override fun onClickDec() {
            if (num == 0) {
                set(value = maxNum)
                return
            }

            set(value = num - 10)
        }

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
