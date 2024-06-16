package viewmodel

import domain.common.status.MonsterStatus
import domain.common.status.Status
import domain.controller.ControllerCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BattleViewModel :
    ControllerCallback {
    var _monsters: MutableStateFlow<MutableList<MonsterStatus>> = MutableStateFlow(mutableListOf())
    var monsters: StateFlow<MutableList<MonsterStatus>> = _monsters.asStateFlow()
    lateinit var playrs: List<Status>

    override lateinit var pressB: () -> Unit

    /**
     * 敵が全滅したかどうかをチェック
     */
    val isAllMonsterNotActive: Boolean
        get() = !monsters.value.any {
            it.isActive
        }

    init {
        initPlayers()
    }

    private fun initPlayers() {
        playrs = List(4) {
            when (it) {
                0 -> {
                    Status().apply {
                        name = "test1"
                        hp.maxPoint = 100
                        hp.point = 50
                        mp.maxPoint = 10
                        mp.point = 5
                    }
                }

                1 -> Status().apply {
                    name = "test2"
                    hp.maxPoint = 100
                    hp.point = 0
                    mp.maxPoint = 111
                    mp.point = 50
                }

                2 -> Status().apply {
                    name = "HPたくさん"
                    hp.maxPoint = 200
                    hp.point = 50
                    mp.maxPoint = 10
                    mp.point = 50
                }

                else -> Status().apply {
                    name = "MPたくさん"
                    hp.maxPoint = 10
                    hp.point = 50
                    mp.maxPoint = 100
                    mp.point = 5
                }
            }
        }
    }

    fun attack(
        target: Int,
        damage: Int,
    ) {
        var actualTarget = target
        //　戦闘不能じゃないtargetを探す
        while (monsters.value[actualTarget].isActive.not()) {
            actualTarget++
            if (monsters.value.size <= actualTarget) {
                actualTarget = 0
            }
        }
        monsters.value[actualTarget].hp.point -= damage

        val newMonsterList = MutableList(monsters.value.size) {
            monsters.value[it]
        }

        _monsters.value = newMonsterList

        if (isAllMonsterNotActive) {
            finishBattle()
        }
    }

    private fun finishBattle() {
        pressB()
    }

    override fun moveStick(dx: Float, dy: Float) {
        // スティック操作に対する処理を実装
    }

    override var pressA = {
        attack(
            target = 0,
            damage = 10,
        )
    }
}
