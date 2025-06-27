package core.usecase.heal

import core.repository.statusdata.StatusDataRepository

class MaxHealUseCaseImpl(
    private val playerStatusRepository: StatusDataRepository,
) : MaxHealUseCase {
    override suspend fun invoke() {
        playerStatusRepository.getStatusList().mapIndexed { index, playerStatus ->
            playerStatusRepository.setStatusData(
                id = index,
                statusData = playerStatus
                    .setHP(
                        value = Int.MAX_VALUE,
                    ).setMP(
                        value = Int.MAX_VALUE,
                    ),
            )
        }
    }
}
