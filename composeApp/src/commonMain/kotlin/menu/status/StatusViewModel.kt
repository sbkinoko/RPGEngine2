package menu.status

import common.Timer
import common.repository.PlayerRepository
import common.repositoryImpl.PlayerRepositoryImpl
import common.status.Status
import controller.domain.ControllerCallback
import controller.domain.StickPosition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import menu.domain.SelectManager

class StatusViewModel : ControllerCallback {
    val repository: PlayerRepository = PlayerRepositoryImpl()

    private var mutableSelectedFlow = MutableStateFlow<Int>(0)
    var selectedFlow = mutableSelectedFlow.asStateFlow()

    private var mutableStatusFlow = MutableStateFlow<Status>(
        repository.getPlayer(selectedFlow.value)
    )
    var statusFlow = mutableStatusFlow.asStateFlow()

    private val selectManager = SelectManager(
        width = 1,
        itemNum = 4,
    )

    fun setSelected(id: Int) {
        selectManager.selected = id
        mutableSelectedFlow.value = id
        mutableStatusFlow.value = repository.getPlayer(id)
    }

    fun getNameAt(id: Int): String {
        return repository.getPlayer(id).name
    }

    private val timer = Timer(200)

    override fun moveStick(stickPosition: StickPosition) {
        if (!timer.isNeedTimePassed()) return

        selectManager.move(stickPosition.toCommand())
        setSelected(selectManager.selected)
    }

    override var pressA: () -> Unit = {}
    override var pressB: () -> Unit = {}
    override var pressM: () -> Unit = {}
}
