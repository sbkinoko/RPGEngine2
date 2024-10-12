package gamescreen.menu.item.skill.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.equalAllocationModifier
import common.values.Colors
import core.WithConfirmAndTextWindow
import gamescreen.menu.component.StatusComponent
import gamescreen.menu.item.skill.SkillList
import gamescreen.menu.item.skill.user.SkillUserViewModel
import org.koin.compose.koinInject

@Composable
fun SkillListWindow(
    modifier: Modifier = Modifier,
    skillUserViewModel: SkillUserViewModel = koinInject(),
    skillListViewModel: SkillListViewModel = koinInject(),
) {
    val user = skillListViewModel.userId

    val flg = remember {
        mutableStateOf(true)
    }

    if (flg.value) {
        skillListViewModel.init()
        flg.value = false
    }

    val selected = skillListViewModel.getSelectedAsState().value
    WithConfirmAndTextWindow(
        modifier = modifier.background(
            color = Colors.MenuBackground,
        ),
        confirmCallBack = {},
        textCallBack = {},
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
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
                    modifier = equalAllocationModifier,
                    text = skillListViewModel.getExplainAt(
                        position = selected,
                    )
                )
                StatusComponent(
                    modifier = equalAllocationModifier
                        .border(
                            width = 1.dp,
                            color = Colors.StatusComponent,
                            shape = RectangleShape,
                        ),
                    statusId = user,
                )
            }
        }
    }
}
