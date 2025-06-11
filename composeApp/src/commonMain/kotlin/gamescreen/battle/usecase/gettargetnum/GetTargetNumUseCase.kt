package gamescreen.battle.usecase.gettargetnum

interface GetTargetNumUseCase {

    operator fun invoke(
        playerId: Int,
    ): Int
}
