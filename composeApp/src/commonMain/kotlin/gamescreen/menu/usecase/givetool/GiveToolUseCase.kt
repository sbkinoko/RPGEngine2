package gamescreen.menu.usecase.givetool

import gamescreen.menu.domain.GiveResult

interface GiveToolUseCase {
    suspend operator fun invoke(): GiveResult
}
