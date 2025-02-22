package common

import NowTime
import getNowTime

class Timer(
    private val awaitTime: Long,
) {
    private var lastUpdateTime: Long = 0
    private val nowTime: NowTime = getNowTime()

    /**
     * 待機時間が経過していたらコールバックを呼び出す
     */
    fun callbackIfTimePassed(
        callback: () -> Unit,
    ) {
        // 時間が立ってなければreturn
        if (nowTime.nowTime - lastUpdateTime < awaitTime) {
            return
        }
        //　最終呼び出し時間を更新
        lastUpdateTime = nowTime.nowTime

        callback()
    }
}
