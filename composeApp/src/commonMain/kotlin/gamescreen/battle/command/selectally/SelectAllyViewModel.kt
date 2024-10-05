package gamescreen.battle.command.selectally

import common.values.playerNum
import core.domain.item.TargetType
import core.domain.item.skill.HealSkill
import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class SelectAllyViewModel : BattleChildViewModel() {
    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()
    private val actionRepository: ActionRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val playerRepository: PlayerRepository by inject()

    private val playerId: Int
        get() {
            val command = commandRepository.nowCommandType as SelectAllyCommand
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
            commandRepository.commandTypeFlow.collect {
                _isAllySelecting.value = it is SelectAllyCommand
                if (isAllySelecting.value) {
                    selectManager.selected = actionRepository.getAction(playerId).ally
                }
            }
        }
    }

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is SelectAllyCommand
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
