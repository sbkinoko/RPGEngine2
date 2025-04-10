package gamescreen.battle.repository.attackeffect

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    private var job = CoroutineScope(Dispatchers.Default).launch { }

    override fun showEffect(id: Int) {
        mutableEffectState.value = effectStateFlow.value.mapIndexed { ind, info ->
            if (ind == id) {
                AttackEffectInfo(rateList.size + 1)
            } else {
                info
            }
        }

        job.cancel()

        job = CoroutineScope(Dispatchers.Default).launch {
            while (
                mutableEffectState.value.any {
                    it.count > 0
                }
            ) {

                delay(50)
                mutableEffectState.value = effectStateFlow.value.map {
                    it.copy(
                        count = it.count - 1,
                    )
                }
            }
        }

        job.start()
    }
}
