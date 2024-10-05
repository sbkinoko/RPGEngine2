package gamescreen.menu.item.skill.usecase.useskill

import core.domain.item.skill.AttackSkill
import core.domain.item.skill.HealSkill
import core.repository.skill.SkillRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import gamescreen.menu.item.skill.repository.skilluser.SkillUserRepository
import gamescreen.menu.item.skill.repository.target.TargetRepository
import gamescreen.menu.item.skill.repository.useid.UseSkillIdRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class UseSkillUseCaseImpl(
    private val targetRepository: TargetRepository,
    private val skillUserRepository: SkillUserRepository,
    private val useSkillIdRepository: UseSkillIdRepository,
    private val skillRepository: SkillRepository,
    private val updateStatusService: UpdatePlayerStatusUseCase
) : UseSkillUseCase {

    override fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val target = targetRepository.target
            val user = skillUserRepository.skillUserId
            val skillId = useSkillIdRepository.skillId

            val skill = skillRepository.getSkill(
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
