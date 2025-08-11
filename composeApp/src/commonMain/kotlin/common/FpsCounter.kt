package common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FpsCounter {
    private val mutableFpsFlow = MutableStateFlow(0L)
    val fpsFlow = mutableFpsFlow.asStateFlow()

    private val q: ArrayDeque<Long> = ArrayDeque()
    private val qSize = 60

    fun addInfo(time: Long) {

        if (q.size != qSize) {
            mutableFpsFlow.value = -1
            q.addLast(time)
            return
        }

        val top = q.first()

        val interval = time - top

        mutableFpsFlow.value = qSize * 1000 / interval

        q.removeFirst()
        q.addLast(time)
    }

    fun clear() {
        q.clear()
    }
}
