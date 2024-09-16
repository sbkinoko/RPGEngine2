package menu.skill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.layout.CenterText
import menu.skill.user.SkillUserViewModel
import org.koin.compose.koinInject

@Composable
fun SkillList(
    selectedId: Int,
    modifier: Modifier,
    skillUserViewModel: SkillUserViewModel = koinInject(),
) {
    Column(
        modifier = modifier,
    ) {
        skillUserViewModel.getSkillAt(selectedId).forEach {
            CenterText(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = skillUserViewModel.getSkillName(it),
            )
        }
    }
}
