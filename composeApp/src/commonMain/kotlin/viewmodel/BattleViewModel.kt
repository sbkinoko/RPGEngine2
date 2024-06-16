package viewmodel

import domain.common.status.MonsterStatus
import domain.common.status.Status
import domain.controller.ControllerCallback

class BattleViewModel() :
    ControllerCallback {
    lateinit var monsters: List<MonsterStatus>
    lateinit var playrs: List<Status>

    override lateinit var pressB: () -> Unit

    /**
     * 敵が全滅したかどうかをチェック
     */
    val isAllMonsterNotActive: Boolean
        get() = !monsters.any {
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
