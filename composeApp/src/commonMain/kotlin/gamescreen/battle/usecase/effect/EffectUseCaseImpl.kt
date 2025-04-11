package gamescreen.battle.usecase.effect

import gamescreen.battle.repository.attackeffect.AttackEffectRepository
import gamescreen.battle.repository.flash.FlashRepository

class EffectUseCaseImpl(
    private val attackEffectRepository: AttackEffectRepository,
    private val flashRepository: FlashRepository,
) : EffectUseCase {

    override fun invoke(
        target: Int,
    ) {
        flashRepository.flash(target)
        attackEffectRepository.showEffect(target)
    }
}
