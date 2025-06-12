package core.usecase.updateparameter

abstract class UpdatePlayerStatusUseCase {
    abstract suspend fun deleteToolAt(
        playerId: Int,
        index: Int,
    )
}
