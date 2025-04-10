package gamescreen.battle.repository.flash

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlashRepositoryImpl : FlashRepository {
    private val mutableFlashStateFlow: MutableStateFlow<List<FlashInfo>> =
        MutableStateFlow(emptyList())

    override val flashStateFlow: StateFlow<List<FlashInfo>>
        get() = mutableFlashStateFlow.asStateFlow()

    override var monsterNum: Int = 0
        set(value) {
            field = value
            mutableFlashStateFlow.value = List(value) {
                FlashInfo(count = 0)
            }
        }

    private var job: Job = CoroutineScope(Dispatchers.Default).launch { }

    override fun flash(id: Int) {
        mutableFlashStateFlow.value = List(monsterNum) {
            if (it == id) {
                // 新規作成
                FlashInfo(6)
            } else {
                // 今までのデータ
                mutableFlashStateFlow.value[it]
            }
        }

        job.cancel()

        job = CoroutineScope(Dispatchers.Default).launch {
            while (flashStateFlow.value.any {
                    // どれか1つでも点滅中なら
                    it.count > 0
                }) {
                mutableFlashStateFlow.value = flashStateFlow.value.map {
                    // カウントを1減らす
                    it.copy(count = it.count - 1)
                }
                delay(100)
            }
        }
        job.start()
    }
}
