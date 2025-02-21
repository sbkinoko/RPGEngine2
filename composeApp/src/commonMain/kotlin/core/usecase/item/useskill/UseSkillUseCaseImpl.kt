package core.usecase.item.useskill

import core.domain.item.skill.AttackSkill
import core.domain.item.skill.HealSkill
import core.repository.player.PlayerStatusRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import data.item.skill.SkillRepository

class UseSkillUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val skillRepository: SkillRepository,
    private val updateStatusService: UpdatePlayerStatusUseCase
) : UseSkillUseCase {
    override suspend fun invoke(
        userId: Int,
        targetId: Int,
        index: Int,
    ) {
        val skillId = playerStatusRepository.getSkill(
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
