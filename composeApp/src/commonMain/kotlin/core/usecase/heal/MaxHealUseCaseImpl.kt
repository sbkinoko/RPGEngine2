package core.usecase.heal

class MaxHealUseCaseImpl(
    private val playerStatusRepository: core.repository.memory.character.statusdata.StatusDataRepository,
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
