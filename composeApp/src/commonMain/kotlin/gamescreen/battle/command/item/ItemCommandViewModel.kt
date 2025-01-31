package gamescreen.battle.command.item

import core.domain.Const
import core.domain.item.TypeKind
import core.repository.player.PlayerStatusRepository
import data.item.ItemRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.SelectAllyCommand
import gamescreen.battle.domain.SelectEnemyCommand
import gamescreen.battle.repository.action.ActionRepository
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject
import kotlin.math.max

abstract class ItemCommandViewModel : BattleChildViewModel() {
    protected val actionRepository: ActionRepository by inject()
    protected val playerStatusRepository: PlayerStatusRepository by inject()

    protected abstract val itemRepository: ItemRepository

    abstract val itemList: List<Int>

    abstract val playerId: Int

    abstract val actionType: ActionType

    private val selectedItemId: Int
        get() = itemList[selectManager.selected]

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
    }

    abstract fun getLastSelectedItemId(): Int

    override fun selectable(): Boolean {
        return canUse(selectedItemId)
    }

    fun getName(id: Int): String {
        return itemRepository.getItem(id).name
    }

    abstract fun canUse(id: Int): Boolean

    override fun goNextImpl() {
        val itemId = selectedItemId
        //　使えないので進まない
        if (canUse(itemId).not()) {
            return
        }

        actionRepository.setAction(
            actionType = actionType,
            playerId = playerId,
            itemId = itemId,
            itemIndex = selectManager.selected,
        )

        when (itemRepository.getItem(itemId)) {
            is TypeKind.AttackItem -> {
                commandRepository.push(
                    SelectEnemyCommand(playerId),
                )
            }

            is TypeKind.HealItem -> {
                commandRepository.push(
                    SelectAllyCommand(playerId),
                )
            }
        }
    }
}
