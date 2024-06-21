package viewmodel

import domain.common.status.MonsterStatus
import domain.common.status.PlayerStatus
import domain.common.status.Status
import domain.common.status.param.HP
import domain.common.status.param.MP
import domain.controller.ControllerCallback

class BattleViewModel :
    ControllerCallback {
    lateinit var monsters: List<MonsterStatus>
    lateinit var playrs: List<Status>

    override lateinit var pressB: () -> Unit

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

    fun attack(
        target: Int,
        damage: Int,
    ) {
        var actualTarget = target
        //　戦闘不能じゃないtargetを探す
        while (monsters[actualTarget].isActive.not()) {
            actualTarget++
            if (monsters.size <= actualTarget) {
                actualTarget = 0
            }
        }

        monsters[actualTarget].hp.point -= damage
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
