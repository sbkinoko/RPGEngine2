package menu.status

import common.Timer
import common.repository.PlayerRepository
import common.repositoryImpl.PlayerRepositoryImpl
import common.status.Status
import controller.domain.Command
import controller.domain.ControllerCallback
import controller.domain.StickPosition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StatusViewModel : ControllerCallback {
    val repository: PlayerRepository = PlayerRepositoryImpl()

    private var mutableSelectedFlow = MutableStateFlow<Int>(0)
    var selectedFlow = mutableSelectedFlow.asStateFlow()

    private var mutableStatusFlow = MutableStateFlow<Status>(
        repository.getPlayer(selectedFlow.value)
    )
    var statusFlow = mutableStatusFlow.asStateFlow()

    fun setSelected(id: Int) {
        mutableSelectedFlow.value = id
        mutableStatusFlow.value = repository.getPlayer(id)
    }

    fun getNameAt(id: Int): String {
        return repository.getPlayer(id).name
    }

    private val timer = Timer(200)

    override fun moveStick(stickPosition: StickPosition) {
        if (!timer.isNeedTimePassed()) return

        when (stickPosition.toCommand()) {
            Command.Up -> {
                moveUp()
            }

            Command.Down -> {
                moveDown()
            }

            else -> Unit
        }
    }

    private fun moveUp() {
        if (selectedFlow.value == 0) {
            setSelected(3)
            return
        }
        setSelected(selectedFlow.value - 1)
    }

    private fun moveDown() {
        if (selectedFlow.value == 3) {
            setSelected(0)
            return
        }
        setSelected(selectedFlow.value + 1)
    }

    override var pressA: () -> Unit = {}
    override var pressB: () -> Unit = {}
    override var pressM: () -> Unit = {}
}
