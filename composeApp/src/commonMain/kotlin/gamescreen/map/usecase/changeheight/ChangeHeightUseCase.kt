package gamescreen.map.usecase.changeheight

import gamescreen.map.domain.ObjectHeight

interface ChangeHeightUseCase {
    suspend operator fun invoke(
        targetHeight: ObjectHeight,
    )
}
