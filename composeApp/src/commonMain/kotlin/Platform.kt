interface NowTime {
    val nowTime: Long
}

expect fun getNowTime(): NowTime
