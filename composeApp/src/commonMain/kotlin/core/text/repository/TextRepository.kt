package core.text.repository

import core.repository.command.CommandRepository

interface TextRepository : CommandRepository<Boolean> {
    fun getText(): String

    fun setText(text: String)
}
