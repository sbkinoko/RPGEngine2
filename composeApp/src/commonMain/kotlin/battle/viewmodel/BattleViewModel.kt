package battle.viewmodel

import NowTime
import battle.domain.CommandState
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SelectedEnemyState
import battle.layout.command.MainCommandCallBack
import battle.layout.command.PlayerActionCallBack
import battle.layout.command.SelectEnemyCallBack
import battle.repository.ActionRepository
import battle.service.AttackService
import battle.service.FindTargetService
import common.repository.PlayerRepository
import common.status.MonsterStatus
import common.status.PlayerStatus
import common.values.playerNum
import controller.domain.ControllerCallback
import getNowTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BattleViewModel :
    ControllerCallback,
    KoinComponent {
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

    private val actionRepository: ActionRepository by inject()
    private val attackService: AttackService by inject()
    private val findTargetService: FindTargetService by inject()
    private val playerRepository: PlayerRepository by inject()

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

    val selectEnemyCallBack = object : SelectEnemyCallBack {
        override val clickMonsterImage: (Int) -> Unit = { monsterId ->
            selectAttackMonster(monsterId)
        }
    }

    init {
        initPlayers()
    }

    private fun initPlayers() {
        players = List(playerNum) {
            playerRepository.getPlayer(id = it)
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
        if (monsters.value[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                monsters = monsters.value,
                target = target,
            )
        }

        mutableMonsters.value = attackService.attack(
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

    private var lastUpdateTime: Long = 0
    private val nowTime: NowTime = getNowTime()
    override fun moveStick(dx: Float, dy: Float) {
        if (nowTime.nowTime - lastUpdateTime < 200) {
            return
        }
        lastUpdateTime = nowTime.nowTime

        when (commandState.value.nowState) {
            is SelectEnemyCommand -> {
                val target = selectedEnemyState.value.selectedEnemy.first()
                if (0.5 <= dx) {
                    setTargetEnemy(
                        findTargetService.findNext(
                            target = target,
                            monsters = monsters.value,
                        )
                    )
                } else if (dx <= -0.5) {
                    setTargetEnemy(
                        findTargetService.findPrev(
                            target = target,
                            monsters = monsters.value,
                        )
                    )
                }
            }

            else -> Unit
        }
    }

    private fun setTargetEnemy(target: Int?) {
        if (target == null) {
            // 矢印削除
            mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
                selectedEnemy = emptyList()
            )
            return
        }

        // fixme 複数選択するようになったら修正
        mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
            selectedEnemy = listOf(target)
        )
    }

    fun selectMainAttack() {
        mutableCommandState.value = commandState.value.push(
            PlayerActionCommand(
                playerId = 0,
            )
        )
    }

    fun selectPlayerAttack(playerId: Int) {
        mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
            selectedEnemy = actionRepository.getAction(playerId).target,
        )
        mutableCommandState.value = commandState.value.push(
            SelectEnemyCommand(playerId)
        )
    }

    fun selectAttackEnemy(playerId: Int) {
        // 行動を保存
        actionRepository.setAction(
            playerId = playerId,
            target = mutableSelectedEnemyState.value.selectedEnemy
        )

        // 矢印削除
        setTargetEnemy(null)

        // 次のコマンドに移動
        if (playerId < playerNum - 1) {
            mutableCommandState.value = commandState.value.push(
                PlayerActionCommand(
                    playerId = playerId + 1,
                )
            )
        } else {
            // fixme　攻撃フェーズに移動
            //　一周したのでリセット
            mutableCommandState.value = CommandState()
        }
    }

    fun selectAttackMonster(monsterId: Int) {
        //　敵を選択中以外は操作しない
        if (selectedEnemyState.value.selectedEnemy.isEmpty()) {
            return
        }

        // すでに選んでる敵を選んだら確定
        if (selectedEnemyState.value.selectedEnemy.first() == monsterId) {
            goNextCommand()
            return
        }

        // 別の敵を選択
        setTargetEnemy(
            monsterId
        )
    }

    override var pressA = {
        goNextCommand()
    }

    private fun goNextCommand() {
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
