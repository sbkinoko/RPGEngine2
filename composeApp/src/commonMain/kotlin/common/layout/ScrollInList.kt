package common.layout

import androidx.compose.foundation.ScrollState

/**
 * targetRowが画面内に入るようにスクロールする処理
 * @param targetRow 移動先の行
 * @param height スクロールエリアの要素の高さ
 * @param itemNum 一度に表示するアイテムの数
 * @param scrollState スクロールしたいリストのstate
 */
suspend fun scrollInList(
    targetRow: Int,
    height: Int,
    itemNum: Int,
    scrollState: ScrollState,
) {
    val targetTop = (targetRow) * height / itemNum
    val targetBottom = (targetRow + 1) * height / itemNum

    val top = scrollState.value
    val bottom = scrollState.value + height

    if (targetTop < top) {
        scrollState.animateScrollTo(targetTop)
    }

    if (bottom < targetBottom) {
        scrollState.animateScrollTo(targetTop - height / itemNum * (itemNum - 1))
    }
}
