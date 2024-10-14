package gamescreen.battle.command.item.skill

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.battle.command.item.ItemCommandWindow
import org.koin.compose.koinInject

@Composable
fun SkillCommandWindow(
    modifier: Modifier = Modifier,
    skillCommandViewModel: SkillCommandViewModel = koinInject(),
) {
    ItemCommandWindow(
        modifier = modifier,
        itemCommandViewModel = skillCommandViewModel,
    )
}
