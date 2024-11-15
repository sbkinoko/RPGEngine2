package gamescreen.menu.item.abstract.target

import core.domain.AbleType
import core.domain.Choice
import core.domain.item.HealItem
import core.repository.item.ItemRepository
import core.repository.player.PlayerStatusRepository
import core.text.repository.TextRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.user.UserRepository
import org.koin.core.component.inject
import values.Constants.Companion.playerNum

abstract class ItemTargetViewModel : MenuChildViewModel() {
    protected val userRepository: UserRepository by inject()
    protected val targetRepository: TargetRepository by inject()
    protected val playerStatusRepository: PlayerStatusRepository by inject()

    private val indexRepository: IndexRepository by inject()

    protected abstract val itemRepository: ItemRepository

    protected val confirmRepository: core.confim.repository.ChoiceRepository by inject()
    protected val textRepository: TextRepository by inject()

    override val canBack: Boolean
        get() = true

    val user: Int
        get() = userRepository.userId

    val skillId: Int
        get() = playerStatusRepository.getSkill(
            user,
            indexRepository.index,
        )

    val explain: String
        get() = itemRepository.getItem(skillId).explain

    protected abstract val boundedMenuType: MenuType

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedMenuType
    }

    override fun goNextImpl() {
        targetRepository.target = selectManager.selected
        confirmRepository.push(listOf(
            Choice(
                text = "yes",
                callBack = {
                    selectYes()

                }
            ),
            Choice(
                text = "no",
                callBack = {
                    confirmRepository.pop()
                }
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
        val targetStatus = playerStatusRepository.getStatus(id = target)
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

}
