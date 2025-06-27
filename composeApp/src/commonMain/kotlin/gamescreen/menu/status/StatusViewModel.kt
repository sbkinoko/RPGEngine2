package gamescreen.menu.status

import common.Timer
import core.domain.status.StatusData
import core.menu.SelectableWindowViewModel
import core.repository.statusdata.StatusDataRepository
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class StatusViewModel(
    private val statusDataRepository: StatusDataRepository,
) : SelectableWindowViewModel(),
    KoinComponent {
    private val menuStateRepository: MenuStateRepository by inject()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = Constants.playerNum,
    )

    override var timer: Timer = Timer(200)

    fun getStatusDataAt(id: Int): StatusData {
        return statusDataRepository.getStatusData(id)
    }

    override fun pressA() {}

    override fun pressB() {
        menuStateRepository.pop()
    }
}
