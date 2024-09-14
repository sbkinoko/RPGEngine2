package battle.command.skill

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText
import common.values.Colors
import org.koin.compose.koinInject

@Composable
fun SkillCommandWindow(
    modifier: Modifier = Modifier,
    skillCommandViewModel: SkillCommandViewModel = koinInject(),
) {
    LaunchedEffect(
        skillCommandViewModel.playerId,
    ) {
        skillCommandViewModel.init()
    }

    Column(modifier = modifier) {
        for (i in 0 until 3) {
            Row(
                modifier = equalAllocationModifier,
            ) {
                SkillArea(
                    index = 2 * i,
                    size = skillCommandViewModel.skillList.size,
                    modifier = equalAllocationModifier,
                )

                SkillArea(
                    index = 2 * i + 1,
                    size = skillCommandViewModel.skillList.size,
                    modifier = equalAllocationModifier,
                )
            }
        }
    }
}

@Composable
fun SkillArea(
    index: Int,
    size: Int,
    modifier: Modifier = Modifier,
) {
    if (index < size) {
        SkillText(
            modifier = modifier,
            position = index,
        )
    } else {
        Spacer(
            modifier = modifier,
        )
    }
}

@Composable
fun SkillText(
    position: Int,
    modifier: Modifier = Modifier,
    skillCommandViewModel: SkillCommandViewModel = koinInject(),
) {
    val id = skillCommandViewModel.skillList[position]
    Box(
        modifier = modifier
            .menuItem(
                id = position,
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
