package gamescreen.menu.debug

import core.menu.SelectCore
import core.menu.SelectCoreInt
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import values.Constants

class DebugViewModel : MenuChildViewModel(),
    KoinComponent {
    private val mutableCollision = MutableStateFlow(true)

    val collisionState = mutableCollision.asStateFlow()

    private val mutableFrame = MutableStateFlow(true)

    val frameState = mutableFrame.asStateFlow()

    override var selectCore: SelectCore<Int> = SelectCoreInt(
        SelectManager(
            width = 1,
            itemNum = Constants.playerNum,
        )
    )

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == MenuType.Debug
    }

    override fun goNextImpl() {
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
}
