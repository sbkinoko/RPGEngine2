package gamescreen.text.repository

import core.domain.TextBoxData
import core.repository.command.CommandRepository

interface TextRepository : CommandRepository<TextBoxData?> {
    val callBack: () -> Unit
    val text: String
}
