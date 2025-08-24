package core.usecase.updateparameter

import core.domain.status.PlayerStatus

class UpdatePlayerStatusUseCaseImpl(
    val characterRepository: core.repository.memory.character.CharacterRepository<PlayerStatus>,
) : UpdatePlayerStatusUseCase() {

    override suspend fun deleteToolAt(
        playerId: Int,
        index: Int,
    ) {
        val status = characterRepository.getStatus(
            id = playerId,
        ).copy(
            toolList = characterRepository.getStatus(
                id = playerId,
            ).toolList.filterIndexed { id, _ ->
                id != index
            }
        )

        characterRepository.setStatus(
            id = playerId,
            status = status,
        )
    }
}
