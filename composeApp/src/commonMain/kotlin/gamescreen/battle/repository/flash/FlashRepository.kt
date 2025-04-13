package gamescreen.battle.repository.flash

import kotlinx.coroutines.flow.StateFlow

interface FlashRepository {
    val flashStateFlow: StateFlow<List<FlashInfo>>

    var monsterNum: Int

    fun setInfo(list: List<FlashInfo>)

}
