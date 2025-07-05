package gamescreen.menu.item.abstract.target

import core.PlayerStatusRepositoryName
import core.domain.AbleType
import core.domain.item.UsableItem
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.text.repository.TextRepository
import org.koin.core.component.inject
import values.Constants.Companion.playerNum

abstract class UsableTargetViewModel<T, V : UsableItem> : TargetViewModel<T, V>() {
    protected val targetRepository: TargetRepository by inject()
    protected val playerStatusRepository: PlayerStatusRepository by inject()
    protected val statusDataRepository: StatusDataRepository by inject(
        qualifier = PlayerStatusRepositoryName
    )

    private val choiceRepository: ChoiceRepository by inject()

    protected val textRepository: TextRepository by inject()

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = playerNum,
        )
    )

    protected abstract val boundedMenuType: MenuType

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedMenuType
    }

    override fun goNextImpl() {
        targetRepository.target = selectCore.stateFlow.value
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


    override fun selectable(id: Int): Boolean {
        return canSelect(id)
    }

    override fun canSelect(target: Int): Boolean {
        val targetStatus = statusDataRepository.getStatusData(id = target)
        val item = itemRepository.getItem(itemId)

        val targetType = item.targetStatusType

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
