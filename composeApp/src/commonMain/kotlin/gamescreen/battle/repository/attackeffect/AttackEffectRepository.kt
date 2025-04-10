package gamescreen.battle.repository.attackeffect

import kotlinx.coroutines.flow.StateFlow

interface AttackEffectRepository {
    val effectStateFlow: StateFlow<List<AttackEffectInfo>>

    var monsterNum: Int

    fun showEffect(id: Int)
}
