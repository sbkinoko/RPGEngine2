package core.domain.item.tool

import core.domain.item.Item

sealed interface Tool : Item {
    // 繰り返し使える
    val isReusable: Boolean

    // 手放せる
    val isDisposable: Boolean
}
