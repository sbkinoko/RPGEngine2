package battle.viewmodel

import battle.manager.AttackManager
import battle.manager.FindTarget
import common.status.MonsterStatus
import common.status.PlayerStatus
import common.status.Status
import common.status.param.HP
import common.status.param.MP
import common.values.playerNum
import controller.domain.ControllerCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BattleViewModel :
    ControllerCallback {
    private var mutableMonsters: MutableStateFlow<List<MonsterStatus>> =
        MutableStateFlow(mutableListOf())
    var monsters: StateFlow<List<MonsterStatus>> = mutableMonsters.asStateFlow()
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
        playrs = List(playerNum) {
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
        val actualTarget = FindTarget().find(
            monsters = monsters.value,
            target = target,
        )

        mutableMonsters.value = AttackManager().attack(
            target = actualTarget,
            damage = damage,
            monsters = monsters.value
        )

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
