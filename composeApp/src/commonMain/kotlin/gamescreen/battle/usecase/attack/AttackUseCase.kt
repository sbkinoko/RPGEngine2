package gamescreen.battle.usecase.attack

interface AttackUseCase {
    suspend operator fun invoke(
        target: Int,
        damage: Int,
    )
}
