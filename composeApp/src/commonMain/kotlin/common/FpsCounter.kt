package common

import getNowTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FpsCounter {
    private val mutableFpsFlow = MutableStateFlow(0L)
    val fpsFlow = mutableFpsFlow.asStateFlow()

    private val q: ArrayDeque<Long> = ArrayDeque()
    private val qSize = 50

    fun addInfo() {
        val time = getNowTime().nowTime
        if (q.size < qSize) {
            q.addLast(time)
            return
        }

        val top = q.first()
        q.removeFirst()
        q.addLast(time)

        mutableFpsFlow.value = (qSize * 1000 / (time - top))

        if (fpsFlow.value <= 30) {
            println("debug")
            println("debug ${fpsFlow.value} $q")
        }
    }
}
