package gamescreen.battle.repository.flash

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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


    override fun setInfo(list: List<FlashInfo>) {
        mutableFlashStateFlow.value = list
    }
}
