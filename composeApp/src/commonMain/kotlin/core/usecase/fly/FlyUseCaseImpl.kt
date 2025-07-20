package core.usecase.fly

import core.repository.mapuistate.MapUiStateRepository
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.ObjectHeightDetail
import gamescreen.map.usecase.changeheight.ChangeHeightUseCase

class FlyUseCaseImpl(
    private val mapUiStateRepository: MapUiStateRepository,
    private val changeHeightUseCase: ChangeHeightUseCase,
) : FlyUseCase {
    override suspend fun invoke() {
        val state = mapUiStateRepository.stateFlow.value

        when (state.player.square.objectHeight) {

            ObjectHeight.None,
            is ObjectHeight.Ground,
            is ObjectHeight.Water,
                -> {
                mapUiStateRepository.updateState(
                    state.copy(
                        player = changeHeightUseCase.invoke(
                            ObjectHeight.Sky(ObjectHeightDetail.Mid),
                            state.player
                        ),
                    )
                )
            }

            is ObjectHeight.Sky -> {
                mapUiStateRepository.updateState(
                    state.copy(
                        player = changeHeightUseCase.invoke(
                            ObjectHeight.Ground(ObjectHeightDetail.Mid),
                            state.player
                        ),
                    )
                )
            }
        }
    }
}
