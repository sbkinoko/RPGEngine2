package gamescreen.map.usecase

import gamescreen.map.domain.DisplayPoint
import gamescreen.map.repository.backgroundcell.BackgroundRepository

class GetScreenCenterUseCase(
    private val backgroundRepository: BackgroundRepository,
) {
    operator fun invoke(): DisplayPoint {
        return DisplayPoint(
            x = backgroundRepository.screenSize / 2f,
            y = backgroundRepository.screenSize / 2f,
        )
    }
}
