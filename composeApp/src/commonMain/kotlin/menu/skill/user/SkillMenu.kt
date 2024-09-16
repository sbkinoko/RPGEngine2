package menu.skill.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.layout.CenterText
import common.values.Colors
import common.values.playerNum
import menu.skill.SkillList
import org.koin.compose.koinInject

@Composable
fun SkillMenu(
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
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            for (i in 0 until playerNum) {
                CenterText(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clickable {
                            skillUserViewModel.setSelected(i)
                        }
                        .border(
                            width = 2.dp,
                            color = if (selectedId == i) Colors.SelectedMenu
                            else Colors.MenuFrame,
                            shape = RectangleShape,
                        ),
                    text = skillUserViewModel.getNameAt(i),
                )
            }
        }

        SkillList(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            skillUserViewModel = skillUserViewModel,
            selectedId = selectedId,
        )
    }
}
