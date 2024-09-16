package menu.skill.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.values.Colors
import menu.skill.SkillList
import menu.skill.user.SkillUserViewModel
import org.koin.compose.koinInject

@Composable
fun SkillListWindow(
    modifier: Modifier = Modifier,
    skillUserViewModel: SkillUserViewModel = koinInject(),
) {
    val selectedId = skillUserViewModel.getSelectedAsState().value
    Row(
        modifier = modifier
            .background(
                color = Colors.MenuBackground,
            )
    ) {
        SkillList(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            skillUserViewModel = skillUserViewModel,
            selectedId = selectedId,
        )
    }
}
