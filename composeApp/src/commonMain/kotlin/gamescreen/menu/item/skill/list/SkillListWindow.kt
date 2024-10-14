package gamescreen.menu.item.skill.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.itemselect.ItemListWindow
import org.koin.compose.koinInject

@Composable
fun SkillListWindow(
    modifier: Modifier = Modifier,
    skillListViewModel: SkillListViewModel = koinInject(),
) {
    ItemListWindow(
        modifier = modifier,
        itemListViewModel = skillListViewModel,
    )
}
