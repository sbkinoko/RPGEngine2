package gamescreen.menushop.domain.amountdata

import common.layout.spinnbutton.SpinButtonData
import controller.domain.ArrowCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AmountDataImpl : AmountData {
    override var maxNum = 99

    val minNum = 1

    override val num
        get() = mutableAmount2.value * 10 +
                mutableAmount1.value

    private val mutableSelectedFlow =
        MutableStateFlow(0)
    override val selected: StateFlow<Int>
        get() = mutableSelectedFlow.asStateFlow()

    private val mutableAmount1 =
        MutableStateFlow(0)
    private val mutableAmount2 =
        MutableStateFlow(0)

    abstract inner class ButtonData : SpinButtonData<Int> {
        abstract val dif: Int

        override fun onClickAdd() {
            if (num == maxNum) {
                set(value = minNum)
                return
            }

            set(value = num + dif)
        }

        override fun onClickDec() {
            if (num == minNum) {
                set(value = maxNum)
                return
            }

            set(value = num - dif)
        }
    }

    override val buttonDataList: List<SpinButtonData<Int>> =
        listOf(
            object : ButtonData() {
                override val dif: Int
                    get() = 10

                override val dataFlow: StateFlow<Int>
                    get() = mutableAmount2.asStateFlow()
            },
            object : ButtonData() {
                override val dif: Int
                    get() = 1

                override val dataFlow: StateFlow<Int>
                    get() = mutableAmount1.asStateFlow()
            },
        )

    override fun set(value: Int) {
        if (value < 0) {
            set(value = minNum)
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

    override fun reset() {
        set(1)
        mutableSelectedFlow.value = 1
    }

    override fun useStick(command: ArrowCommand) {
        when (command) {
            ArrowCommand.Up -> {
                buttonDataList[selected.value].onClickAdd()
            }

            ArrowCommand.Down -> {
                buttonDataList[selected.value].onClickDec()
            }

            ArrowCommand.Right,
            ArrowCommand.Left,
            -> {
                mutableSelectedFlow.value = (mutableSelectedFlow.value + 1) % 2
            }

            ArrowCommand.None -> Unit
        }
    }
}
