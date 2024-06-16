package viewmodel

import domain.common.status.MonsterStatus
import domain.common.status.Status

class BattleViewModel {
    lateinit var monsters: List<MonsterStatus>
    lateinit var playrs: List<Status>

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
        monsters[target].hp.point -= damage
    }
}
