package gamescreen.battle.command.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.selectable
import common.layout.CenterText
import org.koin.compose.koinInject

@Composable
fun BattleMainCommand(
    modifier: Modifier = Modifier,
    battleMainViewModel: BattleMainViewModel = koinInject(),
) {
    val selected = battleMainViewModel.getSelectedAsState().value
    Column(modifier = modifier) {
        Row(
            modifier = equalAllocationModifier,
        ) {
            CenterText(
                modifier = equalAllocationModifier
                    .selectable(
                        id = 0,
                        selected = selected,
                    ).clickable {
                        battleMainViewModel.onClickItem(
                            id = 0,
                        )
                    },
                text = "攻撃",
            )
            CenterText(
                modifier = equalAllocationModifier
                    .selectable(
                        id = 1,
                        selected = selected,
                    ).clickable {
                        battleMainViewModel.onClickItem(
                            id = 1,
                        )
                    },
                text = "逃げる",
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {

        }
    }
}
