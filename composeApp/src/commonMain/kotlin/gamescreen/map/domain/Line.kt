package gamescreen.map.domain

import kotlin.math.max
import kotlin.math.min

data class Line(
    val point1: Point,
    val point2: Point,
) {
    val inclination: Inclination = if (point1.x == point2.x) {
        Inclination.Vertical
    } else {
        Inclination.Slope(
            (point2.y - point1.y) / (point2.x - point1.x)
        )
    }

    val intercept = (point1.x * point2.y - point1.y * point2.x) /
            (point1.x - point2.x)

    private val minY = min(point1.y, point2.y)
    private val maxY = max(point1.y, point2.y)

    //　桁落ちで正確な比較ができないので許容する範囲を設定
    private val range = 0.0001
    private val rowRange = 1 - range
    private val highRange = 1 + range


    fun isInYRange(y: Float) = y in minY * rowRange..maxY * highRange

    fun isInXRange(x: Float) = x in minX * rowRange..maxX * highRange

    private val minX = min(point1.x, point2.x)
    val maxX = max(point1.x, point2.x)
}

sealed class Inclination {
    data object Vertical : Inclination()

    data class Slope(
        val a: Float,
    ) : Inclination()
}

fun Line.isCrossWith(other: Line): Boolean {
    val inclination1 = inclination
    val inclination2 = other.inclination

    if (inclination1 == inclination2) {
        // 傾きが同じ場合

        if (inclination1 is Inclination.Vertical) {
            // 垂直の場合
            if (point1.x != other.point1.x) {
                // x座標が異なる場合、交わらない
                return false
            }
            // yの判断はあと投げ
        } else {
            if (intercept != other.intercept) {
                // 切片が違う場合交わることはない
                return false
            }
        }

        // かぶっている範囲があるかチェック
        return (
                isInYRange(other.point1.y) ||
                        isInYRange(other.point2.y)) &&
                (isInXRange(other.point1.x) ||
                        isInXRange(other.point2.x))
    }

    // 垂直と斜めの場合
    if (inclination1 !is Inclination.Slope) {
        return isCrossing(
            vertical = this,
            slope = other,
        )
    }

    //　垂直と斜めの場合
    if (inclination2 !is Inclination.Slope) {
        return isCrossing(
            vertical = other,
            slope = this,
        )
    }

    // どちらも傾きを出せる場合

    // y = a2x + b2
    // y = a1x + b1
    // → 0 = (a2 - a1)x + b2 - b1
    // → x = - (b2 - b1)/(a2 - a1)
    val x = -(other.intercept - intercept) /
            (inclination2.a - inclination1.a)

    // y = ax + b に基づいて導出
    val y = inclination1.a * x + intercept
    return isInXRange(x) &&
            isInYRange(y) &&
            other.isInYRange(y) &&
            other.isInXRange(x)
}

private fun isCrossing(
    vertical: Line,
    slope: Line,
): Boolean {
    // 垂直なのでxはどちらをとっても同じ
    val x = vertical.maxX

    if (slope.isInXRange(x).not()) {
        // 垂直のxが斜めの範囲外なら交差はしない
        return false
    }

    val a = (slope.inclination as Inclination.Slope).a

    // y = ax + bに基づいてyを導出
    val y = a * x + slope.intercept

    // yが範囲内ならOK
    return slope.isInYRange(y) && vertical.isInYRange(y)
}
