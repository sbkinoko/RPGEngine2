package battle.viewmodel

import NowTime
import battle.domain.AttackPhaseCommand
import battle.domain.CommandState
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SelectedEnemyState
import battle.layout.command.AttackPhaseCommandCallBack
import battle.layout.command.MainCommandCallBack
import battle.layout.command.PlayerActionCallBack
import battle.layout.command.SelectEnemyCallBack
import battle.repository.ActionRepository
import battle.repository.BattleMonsterRepository
import battle.service.FindTargetService
import battle.usecase.AttackUseCase
import common.repository.PlayerRepository
import common.status.MonsterStatus
import common.status.PlayerStatus
import common.values.playerNum
import controller.domain.ControllerCallback
import controller.domain.StickPosition
import getNowTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import main.domain.ScreenType
import main.repository.screentype.ScreenTypeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BattleViewModel :
    ControllerCallback,
    KoinComponent {
    var monsters: StateFlow<List<MonsterStatus>>

    lateinit var players: List<PlayerStatus>

    override var pressB: () -> Unit = {
        screenTypeRepository.screenType = ScreenType.FIELD
    }

    override var pressM: () -> Unit = {}

    private var mutableCommandState: MutableStateFlow<CommandState> =
        MutableStateFlow(CommandState())
    val commandState: StateFlow<CommandState> = mutableCommandState.asStateFlow()

    private var mutableSelectedEnemyState: MutableStateFlow<SelectedEnemyState> =
        MutableStateFlow(SelectedEnemyState(emptyList(), 0))
    val selectedEnemyState: StateFlow<SelectedEnemyState> =
        mutableSelectedEnemyState.asStateFlow()

    private val actionRepository: ActionRepository by inject()
    private val findTargetService: FindTargetService by inject()
    private val playerRepository: PlayerRepository by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()
    private val attackUseCase: AttackUseCase by inject()

    private val screenTypeRepository: ScreenTypeRepository by inject()

    val targetName: String
        get() {
            val targetId = actionRepository.getAction(attackingPlayerId.value).target.first()
            return battleMonsterRepository.getMonster(targetId).name
        }

    /**
     * 敵が全滅したかどうかをチェック
     */
    val isAllMonsterNotActive: Boolean
        get() = !battleMonsterRepository.getMonsters().any {
            it.isActive
        }

    val mainCommandCallback = object : MainCommandCallBack {
        override val attack: () -> Unit = attack@{
            if (commandState.value.nowState !is MainCommand) return@attack
            selectMainAttack()
        }
    }

    val playerCommandCallback = object : PlayerActionCallBack {
        override val attack: () -> Unit = attack@{
            val nowState = ((commandState.value.nowState) as? PlayerActionCommand)
                ?: return@attack
            selectPlayerAttack(
                playerId = nowState.playerId,
            )
        }
    }

    val attackPhaseCommandCallback = object : AttackPhaseCommandCallBack {
        override val pressA: () -> Unit = pressA@{
            if ((commandState.value.nowState) !is AttackPhaseCommand) return@pressA
            attackPhase()
        }
    }

    val selectEnemyCallBack = object : SelectEnemyCallBack {
        override val clickMonsterImage: (Int) -> Unit = { monsterId ->
            selectAttackMonster(monsterId)
        }
    }

    init {
        initPlayers()
        monsters =
            battleMonsterRepository.monsterListFlow.map {
                it
            }.stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    private fun initPlayers() {
        players = List(playerNum) {
            playerRepository.getPlayer(id = it)
        }
    }

    fun setMonsters(monsters: List<MonsterStatus>) {
        CoroutineScope(Dispatchers.Default).launch {
            battleMonsterRepository.setMonster(monsters.toMutableList())
        }
    }

    suspend fun attack(
        target: Int,
        damage: Int,
    ) {
        attackUseCase(
            target = target,
            damage = damage,
        )

        if (isAllMonsterNotActive) {
            finishBattle()
        }
    }

    fun startBattle() {
        screenTypeRepository.screenType = ScreenType.BATTLE
        mutableCommandState.value = CommandState()
        mutableSelectedEnemyState.value = SelectedEnemyState(
            emptyList(),
            monsters.value.size,
        )
        mutableAttackingPlayerId.value = 0
        actionRepository.resetTarget()
    }

    private fun finishBattle() {
        pressB()
    }

    private var lastUpdateTime: Long = 0
    private val nowTime: NowTime = getNowTime()
    override fun moveStick(stickPosition: StickPosition) {
        if (nowTime.nowTime - lastUpdateTime < 200) {
            return
        }
        lastUpdateTime = nowTime.nowTime

        when (commandState.value.nowState) {
            is SelectEnemyCommand -> {
                val target = selectedEnemyState.value.selectedEnemy.first()
                if (0.5 <= stickPosition.ratioX) {
                    setTargetEnemy(
                        findTargetService.findNext(
                            target = target,
                            monsters = monsters.value,
                        )
                    )
                } else if (stickPosition.ratioX <= -0.5) {
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

    private fun selectAttackEnemy(playerId: Int) {
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
            //　一周したので攻撃フェーズに移動
            mutableCommandState.value = mutableCommandState.value.push(AttackPhaseCommand)
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

    private val mutableAttackingPlayerId: MutableStateFlow<Int> = MutableStateFlow(0)
    val attackingPlayerId: StateFlow<Int> = mutableAttackingPlayerId.asStateFlow()

    private fun attackPhase() {
        CoroutineScope(Dispatchers.IO).launch {
            attack(
                target = actionRepository.getAction(attackingPlayerId.value).target.first(),
                damage = 10,
            )
            if (attackingPlayerId.value < playerNum - 1) {
                mutableAttackingPlayerId.value++
            } else {
                mutableAttackingPlayerId.value = 0
                mutableCommandState.value = CommandState()
            }
        }
    }

    fun reloadMonster() {
        CoroutineScope(Dispatchers.IO).launch {
            battleMonsterRepository.reload()
        }
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

            is AttackPhaseCommand -> {
                attackPhase()
            }
        }
    }
}
