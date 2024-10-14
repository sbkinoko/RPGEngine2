package gamescreen.menu.item.skill.usecase.useskill

import core.domain.item.skill.AttackSkill
import core.domain.item.skill.HealSkill
import core.repository.item.skill.SkillRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCaseImpl
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import gamescreen.menu.item.repository.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class UseSkillUseCaseImpl(
    private val targetRepository: TargetRepository,
    private val userRepository: UserRepository,
    private val usedItemIdRepository: UseItemIdRepository,
    private val skillRepository: SkillRepository,
    private val updateStatusService: UpdatePlayerStatusUseCaseImpl
) : UseSkillUseCase {
    override fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val target = targetRepository.target
            val user = userRepository.userId
            val skillId = usedItemIdRepository.itemId

            val skill = skillRepository.getItem(
                id = skillId
            )

            // MP減らす
            updateStatusService.decMP(
                id = user,
                amount = skill.needMP,
            )

            when (skill) {
                is HealSkill -> {
                    updateStatusService.incHP(
                        id = target,
                        amount = skill.healAmount,
                    )
                }

                // 攻撃スキルで来ることはないはず
                is AttackSkill -> Unit
            }
        }
    }
}
