package gamescreen.menu.status

import core.domain.status.StatusData
import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.menu.SelectableChildViewModel
import core.repository.character.statusdata.StatusDataRepository
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class StatusViewModel(
    private val statusDataRepository: StatusDataRepository,
) : SelectableChildViewModel<Int>(),
    KoinComponent {
    private val menuStateRepository: MenuStateRepository by inject()

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = Constants.playerNum,
        )
    )

    override fun goNext() {
        // NOP
    }

    fun getStatusDataAt(id: Int): StatusData {
        return statusDataRepository.getStatusData(id)
    }

    override fun pressA() {}

    override fun pressB() {
        menuStateRepository.pop()
    }
}
