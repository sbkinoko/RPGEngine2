package gamescreen.menu.item.skill.target

import core.domain.AbleType
import core.domain.Place
import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerRepository
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.target.ItemTargetViewModel
import gamescreen.menu.item.skill.usecase.useskill.UseSkillUseCase
import org.koin.core.component.inject

class SkillTargetViewModel : ItemTargetViewModel() {
    private val playerRepository: PlayerRepository by inject()

    override val itemRepository: SkillRepository by inject()

    private val useSkillUseCase: UseSkillUseCase by inject()
    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    override val boundedMenuType: MenuType
        get() = MenuType.SKILL_TARGET

    override fun getAbleType(): AbleType {
        val userStatus = playerRepository.getStatus(id = user)

        return checkCanUseSkillUseCase.invoke(
            status = userStatus,
            skillId = skillId,
            here = Place.MAP,
        )
    }

    /**
     * confirmでYesを選んだ時の処理
     */
    override fun selectYes() {
        // textを表示
        textRepository.push(true)
        textRepository.setText("回復しました")

        //　スキル処理実行
        useSkillUseCase.invoke()
        confirmRepository.pop()
    }
}
