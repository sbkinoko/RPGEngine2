package gamescreen.menu.item.abstract.target

import common.DefaultScope
import core.PlayerStatusRepositoryName
import core.domain.AbleType
import core.domain.item.Item
import core.domain.item.NeedTarget
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.repository.character.player.PlayerCharacterRepository
import core.repository.character.statusdata.StatusDataRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.usecase.usetoolinmap.UseItemInMapUseCase
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import values.Constants.Companion.playerNum

abstract class UsableTargetViewModel<T, V : Item>(
    private val useItemInMapUseCase: UseItemInMapUseCase,
) : TargetViewModel<T, V>() {

    private val targetRepository: TargetRepository by inject()
    protected val playerStatusRepository: PlayerCharacterRepository by inject()
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

        val targetType = (item as NeedTarget).targetStatusType

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
    private fun selectYes() {
        DefaultScope.launch {
            useItemInMapUseCase.invoke()
        }
    }
}
