package gamescreen.menu.item.tool.target

import core.domain.AbleType
import core.repository.item.tool.ToolRepository
import core.text.repository.TextRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.target.ItemTargetViewModel
import org.koin.core.component.inject

class ToolTargetViewModel : ItemTargetViewModel() {
    override val itemRepository: ToolRepository by inject()
    private val textRepository: TextRepository by inject()

    override val boundedMenuType: MenuType
        get() = MenuType.TOOL_TARGET

    override fun getAbleType(): AbleType {
        return AbleType.Able
    }

    override fun selectYes() {
        // textを表示
        textRepository.push(true)
        textRepository.setText("回復しました")
    }
}
