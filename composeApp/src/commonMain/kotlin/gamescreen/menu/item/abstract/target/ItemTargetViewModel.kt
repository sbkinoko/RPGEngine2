package gamescreen.menu.item.abstract.target

import common.values.playerNum
import core.confim.repository.ConfirmRepository
import core.domain.AbleType
import core.domain.item.HealItem
import core.repository.item.ItemRepository
import core.repository.player.PlayerRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import gamescreen.menu.item.repository.user.UserRepository
import org.koin.core.component.inject

abstract class ItemTargetViewModel : MenuChildViewModel() {
    protected val userRepository: UserRepository by inject()
    protected val useItemIdRepository: UseItemIdRepository by inject()
    protected val targetRepository: TargetRepository by inject()
    private val playerRepository: PlayerRepository by inject()

    protected abstract val itemRepository: ItemRepository

    private val confirmRepository: ConfirmRepository by inject()

    override val canBack: Boolean
        get() = true

    val user: Int
        get() = userRepository.userId

    val skillId: Int
        get() = useItemIdRepository.itemId

    val explain: String
        get() = itemRepository.getItem(skillId).explain

    protected abstract val boundedMenuType: MenuType

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedMenuType
    }

    override fun goNextImpl() {
        targetRepository.target = selectManager.selected
        confirmRepository.push(true)
    }

    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )

    override fun selectable(): Boolean {
        return canSelect(selectManager.selected)
    }

    fun canSelect(target: Int): Boolean {
        val targetStatus = playerRepository.getStatus(id = target)
        val skill = itemRepository.getItem(skillId)

        if (skill !is HealItem) {
            // 回復じゃなかったら使えないはず
            return false
        }

        val targetType = skill.targetType

        if (targetType.canSelect(targetStatus).not()) {
            // 対象にとれなかった
            return false
        }

        val ableType = getAbleType()

        return ableType == AbleType.Able
    }

    protected abstract fun getAbleType(): AbleType

    /**
     * confirmでYesを選んだ時の処理
     */
    abstract fun selectYes()

    fun backWindow() {
        commandRepository.pop()
    }
}
