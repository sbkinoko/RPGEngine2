package gamescreen.menu.item.skill.usecase.useskill

import core.domain.item.skill.AttackSkill
import core.domain.item.skill.HealSkill
import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class UseSkillUseCaseImpl(
    private val playerRepository: PlayerRepository,
    private val skillRepository: SkillRepository,
    private val updateStatusService: UpdatePlayerStatusUseCase
) : UseSkillUseCase {
    override fun invoke(
        userId: Int,
        targetId: Int,
        index: Int,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val skillId = playerRepository.getSkill(
                playerId = userId,
                index = index
            )

            val skill = skillRepository.getItem(
                id = skillId
            )

            // MP減らす
            updateStatusService.decMP(
                id = userId,
                amount = skill.needMP,
            )

            when (skill) {
                is HealSkill -> {
                    updateStatusService.incHP(
                        id = targetId,
                        amount = skill.healAmount,
                    )
                }

                // 攻撃スキルで来ることはないはず
                is AttackSkill -> Unit
            }
        }
    }
}
