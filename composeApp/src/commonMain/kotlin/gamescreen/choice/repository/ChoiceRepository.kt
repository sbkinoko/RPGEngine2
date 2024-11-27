package gamescreen.choice.repository

import core.repository.command.CommandRepository
import gamescreen.choice.Choice

interface ChoiceRepository : CommandRepository<List<Choice>>
