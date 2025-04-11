package gamescreen.battle.repository.attackeffect

import kotlinx.coroutines.flow.StateFlow

interface AttackEffectRepository {
    val effectStateFlow: StateFlow<List<AttackEffectInfo>>

    var monsterNum: Int

    fun setEffect(list: List<AttackEffectInfo>)
}
