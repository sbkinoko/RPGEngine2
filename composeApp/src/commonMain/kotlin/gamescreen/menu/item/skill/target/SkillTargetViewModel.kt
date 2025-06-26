package gamescreen.menu.item.skill.target

import core.domain.AbleType
import core.domain.Place
import core.domain.item.Skill
import core.usecase.item.checkcanuseskill.CheckCanUseSkillUseCase
import core.usecase.item.useskill.UseSkillUseCase
import data.item.skill.SkillId
import data.item.skill.SkillRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.target.ItemTargetViewModel
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.text.TextBoxData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class SkillTargetViewModel : ItemTargetViewModel<SkillId, Skill>() {
    override val itemRepository: SkillRepository by inject()

    private val indexRepository: IndexRepository by inject()
    private val useSkillUseCase: UseSkillUseCase by inject()
    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    override val boundedMenuType: MenuType
        get() = MenuType.SKILL_TARGET

    override val itemId: SkillId
        get() = playerStatusRepository.getSkill(
            user,
            indexRepository.index,
        )

    override fun getAbleType(): AbleType {
        val userStatus = statusDataRepository.getStatusData(id = user)

        return checkCanUseSkillUseCase.invoke(
            status = userStatus,
            skillId = itemId,
            here = Place.MAP,
        )
    }

    /**
     * confirmでYesを選んだ時の処理
     */
    override fun selectYes() {
        // textを表示
        textRepository.push(
            TextBoxData(
                text = "回復しました",
                callBack = { commandRepository.pop() },
            )
        )

        //　スキル処理実行
        CoroutineScope(Dispatchers.Default).launch {
            useSkillUseCase.invoke(
                targetId = targetRepository.target,
                userId = userRepository.userId,
                index = indexRepository.index,
            )
        }
    }
}
