package core.repository.choice

import core.domain.Choice

class ChoiceRepositoryImpl : ChoiceRepository {
    override var choiceList: List<Choice> = emptyList()
}
