package gamescreen.battle.repository.attackeffect

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AttackEffectRepositoryImpl : AttackEffectRepository {
    private val mutableEffectState = MutableStateFlow<List<AttackEffectInfo>>(
        emptyList()
    )

    override val effectStateFlow: StateFlow<List<AttackEffectInfo>>
        get() = mutableEffectState

    override var monsterNum: Int = 0
        set(value) {
            field = value
            mutableEffectState.value = List(value) {
                AttackEffectInfo()
            }
        }

    override fun setEffect(list: List<AttackEffectInfo>) {
        mutableEffectState.value = list
    }
}
