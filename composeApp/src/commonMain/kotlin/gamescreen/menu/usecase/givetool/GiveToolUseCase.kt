package gamescreen.menu.usecase.givetool

import gamescreen.menu.domain.GiveResult

interface GiveToolUseCase {
    operator fun invoke(): GiveResult
}
