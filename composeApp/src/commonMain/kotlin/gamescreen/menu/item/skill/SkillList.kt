package gamescreen.menu.item.skill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.menuItem
import common.extension.selectable
import common.layout.CenterText
import gamescreen.menu.item.skill.list.SkillListViewModel
import gamescreen.menu.item.skill.user.SkillUserViewModel
import org.koin.compose.koinInject

@Composable
fun SkillList(
    canSelect: Boolean,
    selectedUserId: Int,
    modifier: Modifier,
    skillUserViewModel: SkillUserViewModel = koinInject(),
    skillListViewModel: SkillListViewModel = koinInject(),
) {
    Column(
        modifier = modifier,
    ) {
        skillUserViewModel.getSkillAt(selectedUserId).forEachIndexed { index, it ->
            CenterText(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .then(
                        if (canSelect) {
                            Modifier.menuItem(
                                id = index,
                                childViewModel = skillListViewModel,
                            )
                        } else {
                            Modifier.selectable(
                                // 選択状態にならないようにしておく
                                id = 1,
                                selected = 2,
                            )
                        }
                    ),
                text = skillUserViewModel.getSkillName(it),
            )
        }
    }
}
