package core.confim

import core.confim.repository.ConfirmRepository
import main.menu.SelectableChildViewModel
import menu.domain.SelectManager
import org.koin.core.component.inject

class ConfirmViewModel : SelectableChildViewModel<Boolean>() {
    override val commandRepository: ConfirmRepository by inject()

    override val canBack: Boolean
        get() = true

    var callBack: () -> Unit = {}

    override fun goNextImpl() {
        when (
            selectManager.selected
        ) {
            ID_YES -> callBack()
            ID_NO -> pressB()
        }
    }


    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = 2,
    )

    override fun isBoundedImpl(commandType: Boolean): Boolean {
        return commandType
    }

    companion object {
        const val ID_YES = 0
        const val ID_NO = 1
    }
}
