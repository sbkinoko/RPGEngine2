package gamescreen.battle.command

import kotlinx.coroutines.flow.StateFlow

interface OnClick2<T> where T : CommandType<T> {

    // fixme Tから取得する方法があれば実装する
    /**
     * T の要素
     */
    val entries: List<T>

    // enum T の要素数
    val itemNum: Int
        get() {
            return entries.size
        }

    val selectedFlowState2: StateFlow<T>

    // Intをenum Tの要素に変換
    fun Int.toEnum(): T {
        if (this < 0 || entries.size <= this) {
            throw IllegalStateException()
        }

        return entries[this]
    }

    // enum T を Intに変換
    fun T.toInt(): Int {
        entries.mapIndexed { index, t ->
            if (t != this) {
                return@mapIndexed
            }
            return@toInt index
        }

        throw IllegalStateException()
    }

    fun onClickItem(
        id: T,
    )
}
