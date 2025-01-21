package gamescreen.menushop.domain.amountdata

import common.layout.spinnbutton.SpinButtonData

interface AmountData {
    var maxNum: Int
    val num: Int

    val buttonData1: SpinButtonData<Int>

    val buttonData10: SpinButtonData<Int>

    fun set(value: Int)

}
