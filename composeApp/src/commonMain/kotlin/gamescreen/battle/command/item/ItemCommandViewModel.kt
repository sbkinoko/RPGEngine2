package gamescreen.battle.command.item

import common.DefaultScope
import core.PlayerStatusRepositoryName
import core.domain.Const
import core.domain.item.Item
import core.domain.item.NeedTarget
import core.domain.item.TargetType
import core.menu.SelectCoreInt
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.repository.monster.item.ItemRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.SelectAllyCommand
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import kotlin.math.max

abstract class ItemCommandViewModel<T, V : Item> : BattleChildViewModel<Int>() {
    protected val actionRepository: ActionRepository by inject()
    protected val playerStatusRepository: PlayerStatusRepository by inject()

    protected val statusDataRepository: StatusDataRepository by inject(
        qualifier = PlayerStatusRepositoryName
    )

    protected abstract val itemRepository: ItemRepository<T, V>

    abstract val itemList: List<T>

    abstract val playerId: Int

    abstract val actionType: ActionType

    private val selectedItemId: T
        get() = itemList[selected]

    abstract fun getLastSelectedItemId(): T

    abstract fun canUse(position: Int): Boolean

    private var job: Job = DefaultScope.launch {}

    val selected
        get() = selectCore.stateFlow.value

    fun init() {
        // 最後に選ばれていたスキルを呼び出し
        val itemId = getLastSelectedItemId()

        val preSelected = max(
            itemList.indexOf(itemId),
            Const.INITIAL_ITEM_POSITION,
        )

        selectCore = SelectCoreInt(
            SelectManager(
                width = 2,
                itemNum = itemList.size,
            )
        )

        selectCore.select(preSelected)

        job.cancel()

        job = DefaultScope.launch {
            selectedFlowState.collect {
                scroll(it)
            }
        }

        job.start()
    }

    override fun selectable(id: Int): Boolean {
        return canUse(id)
    }

    fun getName(position: Int): String {
        val id = itemList[position]
        return itemRepository.getItem(id).name
    }

    override fun goNextImpl() {

        if (canUse(selected).not()) {
            //　今選択しているアイテムは使えないので進まない
            return
        }

        val itemId = selectedItemId

        actionRepository.setAction(
            actionType = actionType,
            playerId = playerId,
            itemId = itemId,
            itemIndex = selected,
        )

        val item = itemRepository.getItem(itemId)
        when ((item as NeedTarget).targetType) {
            TargetType.Ally -> {
                commandRepository.push(
                    SelectAllyCommand(playerId),
                )
            }

            TargetType.Enemy -> {
                commandRepository.push(
                    SelectEnemyCommand(playerId),
                )
            }
        }
    }
}
