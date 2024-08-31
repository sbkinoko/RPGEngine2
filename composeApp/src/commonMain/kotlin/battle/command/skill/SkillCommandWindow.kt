package battle.command.skill

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText
import common.values.Colors

@Composable
fun SkillCommandWindow(
    skillCommandViewModel: SkillCommandViewModel,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(
        skillCommandViewModel.playerId,
    ) {
        skillCommandViewModel.init()
    }

    Column(modifier = modifier) {
        Row(
            modifier = equalAllocationModifier,
        ) {
            SkillText(
                modifier = equalAllocationModifier,
                id = 0,
                skillCommandViewModel = skillCommandViewModel,
            )
            SkillText(
                modifier = equalAllocationModifier,
                skillCommandViewModel = skillCommandViewModel,
                id = 1,
            )
        }
        Row(
            modifier = equalAllocationModifier,
        ) {

        }

        Row(
            modifier = equalAllocationModifier,
        ) {

        }
    }
}

@Composable
fun SkillText(
    skillCommandViewModel: SkillCommandViewModel,
    id: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .menuItem(
                id = id,
                battleChildViewModel = skillCommandViewModel,
            ),
    ) {
        CenterText(
            modifier = Modifier.fillMaxSize(),
            text = skillCommandViewModel.getName(id = id),
        )
        if (skillCommandViewModel.canUse(id = id).not()) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .clickable {

                    }.background(
                        color = Colors.SkillDisabled
                    )
            ) {

            }
        }
    }
}
