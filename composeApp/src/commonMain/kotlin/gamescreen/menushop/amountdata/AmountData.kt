package gamescreen.menushop.amountdata

import kotlinx.coroutines.flow.StateFlow

interface AmountData {
    var maxNum: Int

    val amount1: StateFlow<Int>
    val amount2: StateFlow<Int>
    val amount3: StateFlow<Int>

    fun incAmount1()

    fun incAmount2()

    fun incAmount3()

}
