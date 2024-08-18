package battle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import battle.command.attackphase.AttackPhaseCommandCallBack
import battle.command.main.MainViewModel
import battle.command.playeraction.PlayerActionViewModel
import battle.command.selectenemy.SelectEnemyCallBack
import battle.domain.AttackPhaseCommand
import battle.domain.CommandType
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SelectedEnemyState
import battle.repository.action.ActionRepository
import battle.repository.battlemonster.BattleMonsterRepository
import battle.repository.commandstate.CommandStateRepository
import battle.service.FindTargetService
import battle.usecase.AttackUseCase
import common.Timer
import common.repository.PlayerRepository
import common.status.MonsterStatus
import common.status.PlayerStatus
import common.values.playerNum
import controller.domain.ArrowCommand
import controller.domain.ControllerCallback
import controller.domain.StickPosition
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

    private var mutableSelectedEnemyState: MutableStateFlow<SelectedEnemyState> =
        MutableStateFlow(SelectedEnemyState(emptyList(), 0))
    val selectedEnemyState: StateFlow<SelectedEnemyState> =
        mutableSelectedEnemyState.asStateFlow()

    private val actionRepository: ActionRepository by inject()
    private val findTargetService: FindTargetService by inject()
    private val playerRepository: PlayerRepository by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()
    private val commandStateRepository: CommandStateRepository by inject()

    private val attackUseCase: AttackUseCase by inject()

    private val screenTypeRepository: ScreenTypeRepository by inject()

    val mainViewModel = MainViewModel()
    val playerActionViewModel = PlayerActionViewModel()

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

    val attackPhaseCommandCallback = object : AttackPhaseCommandCallBack {
        override val pressA: () -> Unit = pressA@{
            if ((commandStateRepository.nowCommandType) !is AttackPhaseCommand) return@pressA
            attackPhase()
        }
    }

    val selectEnemyCallBack = object : SelectEnemyCallBack {
        override val clickMonsterImage: (Int) -> Unit = { monsterId ->
            selectAttackMonster(monsterId)
        }
    }

    @Composable
    fun CommandStateFlow(): State<CommandType> {
        return commandStateRepository.commandTypeFlow.collectAsState(
            commandStateRepository.nowCommandType
        )
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
        commandStateRepository.init()
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

    private val timer: Timer = Timer(200)

    override fun moveStick(stickPosition: StickPosition) {
        timer.callbackIfTimePassed {
            when (commandStateRepository.nowCommandType) {
                is MainCommand ->
                    mainViewModel.moveStick(stickPosition)

                is PlayerActionCommand ->
                    playerActionViewModel.moveStick(stickPosition)

                is SelectEnemyCommand ->
                    selectEnemy(stickPosition.toCommand())

                else -> Unit
            }
        }
    }

    private fun selectEnemy(command: ArrowCommand) {
        val target = selectedEnemyState.value.selectedEnemy.first()
        val newTarget = when (command) {
            // 右側の最初のターゲット
            ArrowCommand.Right -> findTargetService.findNext(
                target = target,
                monsters = monsters.value,
            )

            // 左側の最初のターゲット
            ArrowCommand.Left -> findTargetService.findPrev(
                target = target,
                monsters = monsters.value,
            )

            else -> return
        }

        setTargetEnemy(newTarget)
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
            commandStateRepository.push(
                PlayerActionCommand(
                    playerId = playerId + 1,
                )
            )
        } else {
            //　一周したので攻撃フェーズに移動
            commandStateRepository.push(AttackPhaseCommand)
        }
    }

    fun selectAttackMonster(monsterId: Int) {
        //　敵を選択中以外は操作しない
        if (selectedEnemyState.value.selectedEnemy.isEmpty()) {
            return
        }

        // すでに選んでる敵を選んだら確定
        if (selectedEnemyState.value.selectedEnemy.first() == monsterId) {
            pressA()
            return
        }

        // 別の敵を選択
        setTargetEnemy(
            monsterId
        )
    }

    override var pressA = {
        when (
            val nowState = commandStateRepository.nowCommandType
        ) {
            is MainCommand -> {
                mainViewModel.pressA()
            }

            is PlayerActionCommand -> {
                playerActionViewModel.pressA()
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

    fun updateArrow() {
        val playerId =
            (commandStateRepository.nowCommandType as? SelectEnemyCommand)?.playerId ?: return
        val action = actionRepository.getAction(playerId)
        val target = action.target
        mutableSelectedEnemyState.value = mutableSelectedEnemyState.value.copy(
            selectedEnemy = target,
        )
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
                commandStateRepository.init()
            }
        }
    }

    fun reloadMonster() {
        CoroutineScope(Dispatchers.IO).launch {
            battleMonsterRepository.reload()
        }
    }
}
