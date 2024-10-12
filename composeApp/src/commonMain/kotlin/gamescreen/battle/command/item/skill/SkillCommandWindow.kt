package gamescreen.battle.command.item.skill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText
import common.layout.DisableBox
import org.koin.compose.koinInject

@Composable
fun SkillCommandWindow(
    modifier: Modifier = Modifier,
    skillCommandViewModel: SkillCommandViewModel = koinInject(),
) {
    val flg = remember {
        mutableStateOf(true)
    }

    if (flg.value) {
        skillCommandViewModel.init()
        flg.value = false
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
    DisableBox(
        isDisable = skillCommandViewModel.canUse(id = id).not(),
        modifier = modifier
            .menuItem(
                id = position,
                childViewModel = skillCommandViewModel,
            )
    ) {
        CenterText(
            modifier = Modifier.fillMaxSize(),
            text = skillCommandViewModel.getName(id = id),
        )
    }
}
