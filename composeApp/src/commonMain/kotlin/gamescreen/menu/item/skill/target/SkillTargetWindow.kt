package gamescreen.menu.item.skill.target

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.target.ItemTargetWindow
import org.koin.compose.koinInject

@Composable
fun SkillTargetWindow(
    modifier: Modifier = Modifier,
    skillTargetViewModel: SkillTargetViewModel = koinInject(),
) {
    ItemTargetWindow(
        modifier = modifier,
        itemTargetViewModel = skillTargetViewModel,
    )
}
