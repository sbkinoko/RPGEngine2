package battle.viewmodel

import battle.domain.CommandState
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SelectedEnemyState
import battle.layout.command.MainCommandCallBack
import battle.layout.command.PlayerActionCallBack
import battle.manager.AttackManager
import battle.manager.FindTarget
import common.status.MonsterStatus
import common.status.PlayerStatus
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

    lateinit var players: List<PlayerStatus>

    override lateinit var pressB: () -> Unit

    private var mutableCommandState: MutableStateFlow<CommandState> =
        MutableStateFlow(CommandState())
    val commandState: StateFlow<CommandState> = mutableCommandState.asStateFlow()

    private var mutableSelectedEnemyState: MutableStateFlow<SelectedEnemyState> =
        MutableStateFlow(SelectedEnemyState(emptyList(), 0))
    val selectedEnemyState: StateFlow<SelectedEnemyState> =
        mutableSelectedEnemyState.asStateFlow()

    /**
     * 敵が全滅したかどうかをチェック
     */
    val isAllMonsterNotActive: Boolean
        get() = !monsters.value.any {
            it.isActive
        }

    val mainCommandCallback = object : MainCommandCallBack {
        override val attack: () -> Unit = {
            selectMainAttack()
        }
    }

    val playerCommandCallback = object : PlayerActionCallBack {
        override val attack: () -> Unit = {
            val nowState = ((commandState.value.nowState) as PlayerActionCommand)
            selectPlayerAttack(
                playerId = nowState.playerId,
            )
        }
    }

    init {
        initPlayers()
    }

    private fun initPlayers() {
        players = List(playerNum) {
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

    fun startBattle() {
        mutableCommandState.value = CommandState()
        mutableSelectedEnemyState.value = SelectedEnemyState(
            emptyList(),
            monsters.value.size,
        )
    }

    private fun finishBattle() {
        pressB()
    }

    override fun moveStick(dx: Float, dy: Float) {
        // スティック操作に対する処理を実装
    }

    fun selectMainAttack() {
        mutableCommandState.value = commandState.value.push(
            PlayerActionCommand(
                playerId = 0,
            )
        )
    }

    fun selectPlayerAttack(playerId: Int) {
        mutableCommandState.value = commandState.value.push(
            SelectEnemyCommand(playerId)
        )
        mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
            selectedEnemy = listOf(1),
        )
    }

    fun selectAttackEnemy(playerId: Int) {
        if (playerId < playerNum - 1) {
            mutableCommandState.value = commandState.value.push(
                PlayerActionCommand(
                    playerId = playerId + 1,
                )
            )
            // todo repositoryに行動をセットする
        } else {
            finishBattle()
        }

        // 矢印削除
        mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
            selectedEnemy = emptyList()
        )
    }

    override var pressA = {
        when (
            val nowState = commandState.value.nowState
        ) {
            is MainCommand -> {
                selectMainAttack()
            }

            is PlayerActionCommand -> {
                selectPlayerAttack(
                    playerId = nowState.playerId
                )
            }

            is SelectEnemyCommand -> {
                selectAttackEnemy(
                    playerId = nowState.playerId,
                )
            }
        }
    }
}
