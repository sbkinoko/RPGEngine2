package constants

const val REPEAT_TIME = 1000
const val ACCEPT_RANGE = 0.2
const val BORDER_LOWER = ((1 - ACCEPT_RANGE) * REPEAT_TIME).toInt()
const val BORDER_UPPER = ((1 + ACCEPT_RANGE) * REPEAT_TIME).toInt()
