package battle.command.selectally

import battle.BattleChildViewModel
import battle.domain.BattleCommandType
import battle.domain.HealSkill
import battle.domain.SelectAllyCommand
import battle.domain.TargetType
import battle.repository.action.ActionRepository
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import common.values.playerNum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import main.repository.player.PlayerRepository
import main.repository.skill.SkillRepository
import menu.domain.SelectManager
import org.koin.core.component.inject

class SelectAllyViewModel : BattleChildViewModel() {
    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()
    private val actionRepository: ActionRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val playerRepository: PlayerRepository by inject()

    private val playerId: Int
        get() {
            val command = commandStateRepository.nowBattleCommandType as SelectAllyCommand
            return command.playerId
        }

    private val _isAllySelecting: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAllySelecting = _isAllySelecting.asStateFlow()

    override val canBack: Boolean
        get() = true

    val targetType: TargetType
        get() {
            val skillId = actionRepository.getAction(playerId).skillId!!
            val skill = skillRepository.getSkill(skillId)
            if (skill !is HealSkill) {
                throw RuntimeException("Heal以外でここにいないはず")
            }
            return skill.targetType
        }

    init {
        CoroutineScope(Dispatchers.Default).launch {
            commandStateRepository.battleCommandTypeFlow.collect {
                _isAllySelecting.value = it is SelectAllyCommand
                if (isAllySelecting.value) {
                    selectManager.selected = actionRepository.getAction(playerId).ally
                }
            }
        }
    }

    override fun isBoundedImpl(battleCommandType: BattleCommandType): Boolean {
        return battleCommandType is SelectAllyCommand
    }

    override fun selectable(): Boolean {
        val id = selectManager.selected
        val status = playerRepository.getStatus(id)
        return targetType.canSelect(status)
    }

    override fun goNextImpl() {
        // ターゲットを保存
        actionRepository.setAlly(
            playerId = playerId,
            // 表示ようにリストになっていたが、保存する際は先頭だけ保存して、
            //　実際に攻撃するときに攻撃対象を再計算する
            allyId = selectManager.selected,
        )
        changeSelectingActionPlayerUseCase.invoke()
    }

    override var selectManager: SelectManager = SelectManager(
        width = playerNum,
        itemNum = playerNum,
    )
}
