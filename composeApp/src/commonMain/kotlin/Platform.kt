interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


interface NowTime {
    val nowTime: Long
}

expect fun getNowTime(): NowTime
