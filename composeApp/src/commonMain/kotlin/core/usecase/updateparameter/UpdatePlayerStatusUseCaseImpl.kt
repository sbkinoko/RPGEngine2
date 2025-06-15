package core.usecase.updateparameter

import core.domain.status.PlayerStatus
import core.repository.status.StatusRepository

class UpdatePlayerStatusUseCaseImpl(
    val statusRepository: StatusRepository<PlayerStatus>,
) : UpdatePlayerStatusUseCase() {

    override suspend fun deleteToolAt(
        playerId: Int,
        index: Int,
    ) {
        val status = statusRepository.getStatus(
            id = playerId,
        ).copy(
            toolList = statusRepository.getStatus(
                id = playerId,
            ).toolList.filterIndexed { id, _ ->
                id != index
            }
        )

        statusRepository.setStatus(
            id = playerId,
            status = status,
        )
    }
}
