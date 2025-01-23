package gamescreen.menushop.domain.amountdata

import common.layout.spinnbutton.SpinButtonData
import controller.domain.ArrowCommand
import kotlinx.coroutines.flow.StateFlow

interface AmountData {
    var maxNum: Int
    val num: Int

    val selected: StateFlow<Int>
    fun useStick(command: ArrowCommand)

    val buttonDataList: List<SpinButtonData<Int>>

    fun set(value: Int)

    fun reset()
}
