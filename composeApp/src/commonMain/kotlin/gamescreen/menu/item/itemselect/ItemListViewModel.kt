package gamescreen.menu.item.itemselect

import core.domain.AbleType
import core.domain.Place
import core.repository.item.ItemRepository
import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerRepository
import core.text.repository.TextRepository
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.ItemList
import gamescreen.menu.item.skill.repository.skilluser.SkillUserRepository
import gamescreen.menu.item.skill.repository.useid.UseSkillIdRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ItemListViewModel : MenuChildViewModel(),
    ItemList,
    KoinComponent {
    protected val playerRepository: PlayerRepository by inject()
    private val skillUserRepository: SkillUserRepository by inject()
    private val useSkillIdRepository: UseSkillIdRepository by inject()
    private val skillRepository: SkillRepository by inject()
    protected abstract val itemRepository: ItemRepository

    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    private val textRepository: TextRepository by inject()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = 1,
    )

    override val canBack: Boolean
        get() = true

    override fun goNextImpl() {
        val position = selectManager.selected
        val skillId = playerRepository.getStatus(userId).skillList[position]
        val status = playerRepository.getStatus(userId)

        val ableType = checkCanUseSkillUseCase.invoke(
            skillId = skillId,
            status = status,
            here = Place.MAP,
        )
        when (ableType) {
            AbleType.Able -> {
                // skillIdを保存
                useSkillIdRepository.skillId = skillId
                //　次の画面に遷移
                commandRepository.push(
                    MenuType.SKILL_TARGET,
                )
            }

            AbleType.CANT_USE_BY_PLACE -> {
                textRepository.setText("ここでは使えません")
                textRepository.push(true)
            }

            AbleType.CANT_USE_BY_MP -> {
                textRepository.setText("MPがたりません")
                textRepository.push(true)
            }
        }
    }

    protected abstract val boundedScreenType: MenuType

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == boundedScreenType
    }

    val userId: Int
        get() = skillUserRepository.skillUserId

    protected abstract val itemList: List<Int>

    fun init() {
        loadItem()
    }

    private fun loadItem() {
        selectManager = SelectManager(
            width = 1,
            itemNum = itemList.size
        )
        selectManager.selected = INIT_ITEM_POSITION
    }

    fun getExplainAt(position: Int): String {
        val skillId = playerRepository.getStatus(userId).skillList[position]
        return skillRepository.getSkill(skillId).explain
    }

    override fun pressB() {
        commandRepository.pop()
    }

    override fun getItemName(id: Int): String {
        return itemRepository.getItem(id).name
    }

    companion object {
        private const val INIT_ITEM_POSITION = 0
    }
}
