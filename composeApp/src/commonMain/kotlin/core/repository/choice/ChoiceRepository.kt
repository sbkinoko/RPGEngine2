package core.repository.choice

import core.domain.Choice

interface ChoiceRepository {
    var choiceList: List<Choice>
}
