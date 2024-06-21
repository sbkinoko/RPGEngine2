package viewmodel

import domain.common.status.MonsterStatus
import domain.common.status.PlayerStatus
import domain.common.status.Status
import domain.common.status.param.HP
import domain.common.status.param.MP
import domain.controller.ControllerCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BattleViewModel :
    ControllerCallback {
    private var mutableMonsters: MutableStateFlow<MutableList<MonsterStatus>> =
        MutableStateFlow(mutableListOf())
    var monsters: StateFlow<MutableList<MonsterStatus>> = mutableMonsters.asStateFlow()
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
                0 -> PlayerStatus(
                    name = "test1",
                    hp = HP(
                        maxValue = 100,
                        value = 50,
                    ),
                    mp = MP(
                        maxValue = 10,
                        value = 5,
                    )
                )

                1 -> PlayerStatus(
                    name = "test2",
                    hp = HP(
                        maxValue = 100,
                        value = 0,
                    ),
                    mp = MP(
                        maxValue = 111,
                        value = 50,
                    )
                )

                2 -> PlayerStatus(
                    name = "HPたくさん",
                    hp = HP(
                        maxValue = 200,
                        value = 50,
                    ),
                    mp = MP(
                        maxValue = 10,
                        value = 50,
                    )
                )

                else -> PlayerStatus(
                    name = "MPたくさん",
                    hp = HP(
                        maxValue = 10,
                        value = 50,
                    ),
                    mp = MP(
                        maxValue = 100,
                        value = 50,
                    )
                )
            }
        }
    }

    fun setMonsters(monsters: List<MonsterStatus>) {
        mutableMonsters.value = monsters.toMutableList()
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

        mutableMonsters.value = mutableMonsters.value
            //　ダメージを与えた敵だけ新しいインスタンスに変更
            .mapIndexed { index, monsterStatus ->
                if (index != actualTarget) {
                    monsterStatus
                } else {
                    monsterStatus.copy(
                        hp = monsterStatus.hp.copy(
                            value = monsterStatus.hp.value - damage
                        )
                    )
                }
            }.toMutableList()

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
