package map.usecase

import map.domain.Point
import map.repository.backgroundcell.BackgroundRepository

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
