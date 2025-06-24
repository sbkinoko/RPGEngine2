package common.error

class StatusParameterException(
    str: String = "不正な値の入力",
) : RuntimeException(
    str
)
