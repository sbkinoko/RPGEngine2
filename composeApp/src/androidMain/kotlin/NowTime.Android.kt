class AndroidNowTime : NowTime {
    override val nowTime: Long
        get() = System.currentTimeMillis()
}

actual fun getNowTime(): NowTime = AndroidNowTime()
