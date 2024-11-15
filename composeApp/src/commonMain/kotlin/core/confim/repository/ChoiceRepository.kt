package core.confim.repository

import core.domain.Choice
import core.repository.command.CommandRepository

interface ChoiceRepository : CommandRepository<List<Choice>>
