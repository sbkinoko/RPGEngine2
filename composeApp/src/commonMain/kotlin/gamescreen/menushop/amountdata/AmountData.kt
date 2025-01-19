package gamescreen.menushop.amountdata

import kotlinx.coroutines.flow.StateFlow

interface AmountData {
    var maxNum: Int
    val num: Int

    val amount1: StateFlow<Int>
    val amount2: StateFlow<Int>

    fun set(value: Int)

    fun incAmount1()

    fun incAmount2()

}
