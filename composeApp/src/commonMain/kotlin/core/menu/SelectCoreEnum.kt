package core.menu

import common.DefaultScope
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SelectCoreEnum<T>(
    override val entries: List<T>,
    manager: SelectManager = SelectManager(
        2,
        entries.size
    ),
) : SelectCore<T>() {
    private var job = DefaultScope.launch { }

    override var selectManager: SelectManager = manager
        set(value) {
            startCollect()
            field = value
        }

    private val mutableSelectFlow: MutableStateFlow<T> = MutableStateFlow(manager.selected.toEnum())

    override val stateFlow: StateFlow<T>
        get() = mutableSelectFlow.asStateFlow()

    // enum T の要素数
    val itemNum: Int
        get() {
            return entries.size
        }

    init {
        startCollect()
    }

    private fun startCollect() {
        job.cancel()
        job = DefaultScope.launch {
            selectManager.selectedFlowState.collect {
                mutableSelectFlow.value = it.toEnum()
            }
        }
        job.start()
    }

    override fun select(id: T) {
        selectManager.selected = id.toInt()
    }

    override fun click(
        id: T,
        goNext: () -> Unit,
    ) {
        if (stateFlow.value == id) {
            goNext.invoke()
            return
        }

        selectManager.selected = id.toInt()
    }

    // IntをTの要素に変換
    private fun Int.toEnum(): T {
        if (this < 0 || entries.size <= this) {
            throw IllegalStateException()
        }

        return entries[this]
    }

    // T を Intに変換
    private fun T.toInt(): Int {
        entries.mapIndexed { index, t ->
            if (t != this) {
                return@mapIndexed
            }
            return@toInt index
        }

        throw IllegalStateException()
    }
}
