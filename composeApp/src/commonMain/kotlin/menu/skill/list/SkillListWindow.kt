package menu.skill.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import common.values.Colors
import menu.skill.SkillList
import menu.skill.user.SkillUserViewModel
import org.koin.compose.koinInject

@Composable
fun SkillListWindow(
    modifier: Modifier = Modifier,
    skillUserViewModel: SkillUserViewModel = koinInject(),
    skillListViewModel: SkillListViewModel = koinInject(),
) {
    val user = skillListViewModel.user

    val flg = remember {
        mutableStateOf(true)
    }

    if (flg.value) {
        skillListViewModel.init()
        flg.value = false
    }

    val skillId = skillListViewModel.getSelectedAsState().value
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
            selectedUserId = user,
            canSelect = true,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            Text(
                text = skillListViewModel.getExplainAt(
                    id = skillId,
                )
            )
        }
    }
}
