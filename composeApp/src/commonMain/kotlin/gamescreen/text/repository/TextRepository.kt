package gamescreen.text.repository

import core.repository.command.CommandRepository
import gamescreen.text.TextBoxData

interface TextRepository : CommandRepository<TextBoxData?> {
    val callBack: () -> Unit
    val text: String
}
