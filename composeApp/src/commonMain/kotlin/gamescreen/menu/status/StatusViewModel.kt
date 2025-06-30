package gamescreen.menu.status

import core.domain.status.StatusData
import core.menu.IntSelectableChildViewModel
import core.repository.statusdata.StatusDataRepository
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class StatusViewModel(
    private val statusDataRepository: StatusDataRepository,
) : IntSelectableChildViewModel(),
    KoinComponent {
    private val menuStateRepository: MenuStateRepository by inject()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = Constants.playerNum,
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
