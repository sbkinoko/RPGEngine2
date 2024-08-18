package menu.status

import common.Timer
import common.menu.CommonMenuViewModel
import common.repository.PlayerRepository
import common.repositoryImpl.PlayerRepositoryImpl
import common.status.Status
import menu.domain.SelectManager

class StatusViewModel : CommonMenuViewModel() {
    val repository: PlayerRepository = PlayerRepositoryImpl()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = 4,
    )

    override var timer: Timer = Timer(200)

    fun getStatusAt(id: Int): Status {
        return repository.getPlayer(id)
    }

    override var pressA: () -> Unit = {}
}
