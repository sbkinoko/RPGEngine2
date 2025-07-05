package gamescreen.menu

import core.menu.SelectableChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.repository.menustate.MenuStateRepository
import gamescreen.menu.usecase.backfield.CloseMenuUseCase
import org.koin.core.component.inject

abstract class MenuChildViewModel : SelectableChildViewModel<Int>() {
    val commandRepository: MenuStateRepository by inject()
    private val closeMenuUseCase: CloseMenuUseCase by inject()

    private fun isBoundCommand(): Boolean {
        return isBoundedImpl(
            commandRepository.nowMenuType
        )
    }

    protected abstract fun isBoundedImpl(
        commandType: MenuType,
    ): Boolean

    override fun goNext() {
        // 別の画面状態ならなにもしない
        if (isBoundCommand().not()) {
            return
        }

        goNextImpl()
    }

    protected abstract fun goNextImpl()

    override fun pressB() {
        commandRepository.pop()
    }

    override fun pressM() {
        closeMenuUseCase.invoke()
    }
}
