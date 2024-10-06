package gamescreen.battle.command.item

import core.domain.Const
import core.domain.item.HealItem
import core.repository.item.ItemRepository
import core.repository.player.PlayerRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.ActionType
import gamescreen.battle.domain.SelectAllyCommand
import gamescreen.battle.repository.action.ActionRepository
import org.koin.core.component.inject
import kotlin.math.max

abstract class ItemCommandViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val itemRepository: ItemRepository by inject()
    protected val playerRepository: PlayerRepository by inject()

    abstract val itemList: List<Int>

    abstract val playerId: Int

    abstract val actionType: ActionType

    override val canBack: Boolean
        get() = true

    private val selectedItemId: Int
        get() = itemList[selectManager.selected]


    fun init() {
        // 最後に選ばれていたスキルを呼び出し
        val itemId = actionRepository.getAction(
            playerId = playerId
        ).skillId

        // skillIdを発見できない場合は先頭を返す
        //　それ以外はskillIdの場所を返す
        selectManager.selected = max(
            itemList.indexOf(itemId),
            Const.INITIAL_PLAYER,
        )
    }

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
            skillId = itemId,
        )

        when (itemRepository.getItem(itemId)) {
            is HealItem -> {
                commandRepository.push(
                    SelectAllyCommand(playerId),
                )
            }
        }
    }
}
