package gamescreen.menu.item.skill.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.user.UserWindow
import org.koin.compose.koinInject

@Composable
fun SkillUserWindow(
    modifier: Modifier = Modifier,
    skillUserViewModel: SkillUserViewModel = koinInject(),
) {
    UserWindow(
        modifier = modifier,
        itemUserViewModel = skillUserViewModel,
    )
}
