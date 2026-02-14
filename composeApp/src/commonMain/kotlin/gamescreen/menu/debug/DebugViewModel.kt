package gamescreen.menu.debug

import core.menu.SelectCore
import core.menu.SelectCoreInt
import core.menu.SelectableChildViewModel
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.repository.menustate.MenuStateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class DebugViewModel : SelectableChildViewModel<Int>(),
    KoinComponent {
    private val mutableCollision = MutableStateFlow(true)

    val collisionState = mutableCollision.asStateFlow()

    private val mutableFrame = MutableStateFlow(true)

    val frameState = mutableFrame.asStateFlow()

    private val menuStateRepository: MenuStateRepository by inject()

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = Constants.playerNum,
        )
    )

    override fun pressA() {
    }

    override fun goNext() {
        when (selectedFlowState.value) {
            0 -> {
                mutableCollision.value = !collisionState.value
            }

            1 -> {
                mutableFrame.value = !frameState.value
            }
        }
    }

    fun getText(id: Int): String {
        when (id) {
            0 -> {
                return "当たり判定:"
            }

            1 -> {
                return "マスの枠:"
            }
        }

        throw NotImplementedError()
    }

    override fun pressB() {
        menuStateRepository.pop()
    }
}
