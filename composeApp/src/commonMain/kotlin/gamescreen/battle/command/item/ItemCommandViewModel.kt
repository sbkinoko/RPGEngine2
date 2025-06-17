package gamescreen.battle.command.item

import core.PlayerStatusRepositoryName
import core.domain.Const
import core.domain.item.TargetType
import core.domain.status.StatusType
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.item.ItemRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.SelectAllyCommand
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import kotlin.math.max

abstract class ItemCommandViewModel<T> : BattleChildViewModel() {
    protected val actionRepository: ActionRepository by inject()
    protected val playerStatusRepository: PlayerStatusRepository by inject()

    protected val statusDataRepository: StatusDataRepository<StatusType.Player> by inject(
        qualifier = PlayerStatusRepositoryName
    )

    protected abstract val itemRepository: ItemRepository<T>

    var scroll: (Int) -> Unit = {}

    abstract val itemList: List<T>

    abstract val playerId: Int

    abstract val actionType: ActionType

    private val selectedItemId: T
        get() = itemList[selectManager.selected]

    abstract fun getLastSelectedItemId(): T

    abstract fun canUse(position: Int): Boolean

    private var job: Job = CoroutineScope(Dispatchers.Default).launch {}

    fun init() {
        // 最後に選ばれていたスキルを呼び出し
        val itemId = getLastSelectedItemId()

        val selected = max(
            itemList.indexOf(itemId),
            Const.INITIAL_ITEM_POSITION,
        )

        selectManager = SelectManager(
            width = 2,
            itemNum = itemList.size,
        )

        selectManager.selected = selected


        job.cancel()

        job = CoroutineScope(Dispatchers.Default).launch {
            selectedFlowState.collect {
                scroll(it)
            }
        }

        job.start()
    }

    override fun selectable(): Boolean {
        return canUse(selectManager.selected)
    }

    fun getName(position: Int): String {
        val id = itemList[position]
        return itemRepository.getItem(id).name
    }

    override fun goNextImpl() {

        if (canUse(selectManager.selected).not()) {
            //　今選択しているアイテムは使えないので進まない
            return
        }

        val itemId = selectedItemId

        actionRepository.setAction(
            actionType = actionType,
            playerId = playerId,
            itemId = itemId,
            itemIndex = selectManager.selected,
        )

        when (itemRepository.getItem(itemId).targetType) {
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
