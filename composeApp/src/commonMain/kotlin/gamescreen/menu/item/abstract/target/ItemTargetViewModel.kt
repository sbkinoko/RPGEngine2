package gamescreen.menu.item.abstract.target

import core.PlayerStatusRepositoryName
import core.domain.AbleType
import core.domain.status.StatusType
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import data.item.ItemRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.text.repository.TextRepository
import org.koin.core.component.inject
import values.Constants.Companion.playerNum

abstract class ItemTargetViewModel<T> : MenuChildViewModel() {
    protected val userRepository: UserRepository by inject()
    protected val targetRepository: TargetRepository by inject()
    protected val playerStatusRepository: PlayerStatusRepository by inject()
    protected val statusDataRepository: StatusDataRepository<StatusType.Player> by inject(
        qualifier = PlayerStatusRepositoryName
    )

    private val choiceRepository: ChoiceRepository by inject()

    protected abstract val itemRepository: ItemRepository<T>

    protected val textRepository: TextRepository by inject()

    val user: Int
        get() = userRepository.userId

    abstract val itemId: T

    val explain: String
        get() = itemRepository.getItem(itemId).explain

    protected abstract val boundedMenuType: MenuType

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedMenuType
    }

    override fun goNextImpl() {
        targetRepository.target = selectManager.selected
        choiceRepository.push(
            listOf(
                Choice(
                    text = "yes",
                    callBack = {
                        selectYes()
                    }
                ),
                Choice(
                    text = "no",
                    callBack = { }
                ),
            ))
    }

    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )

    override fun selectable(): Boolean {
        return canSelect(selectManager.selected)
    }

    fun canSelect(target: Int): Boolean {
        val targetStatus = statusDataRepository.getStatusData(id = target)
        val skill = itemRepository.getItem(itemId)

        val targetType = skill.targetStatusType

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
}
