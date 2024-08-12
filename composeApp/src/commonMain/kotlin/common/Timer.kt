package common

import NowTime
import getNowTime

class Timer(
    private val awaitTime: Long,
) {
    private var lastUpdateTime: Long = 0
    private val nowTime: NowTime = getNowTime()

    /**
     * 待機時間の分だけ経過しているかどうかをチェック
     */
    fun isNeedTimePassed(): Boolean {
        if (nowTime.nowTime - lastUpdateTime < awaitTime) {
            return false
        }
        lastUpdateTime = nowTime.nowTime

        return true
    }
}
