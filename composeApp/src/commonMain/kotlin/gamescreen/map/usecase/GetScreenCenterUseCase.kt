package gamescreen.map.usecase

import gamescreen.map.domain.Point
import gamescreen.map.repository.backgroundcell.BackgroundRepository

class GetScreenCenterUseCase(
    private val backgroundRepository: BackgroundRepository,
) {
    operator fun invoke(): Point {
        return Point(
            x = backgroundRepository.screenSize / 2f,
            y = backgroundRepository.screenSize / 2f,
        )
    }
}
