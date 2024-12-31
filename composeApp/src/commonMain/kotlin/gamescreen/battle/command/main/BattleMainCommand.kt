package gamescreen.battle.command.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText
import org.koin.compose.koinInject
import values.TextData

@Composable
fun BattleMainCommand(
    modifier: Modifier = Modifier,
    battleMainViewModel: BattleMainViewModel = koinInject(),
) {
    Column(modifier = modifier) {
        Row(
            modifier = equalAllocationModifier,
        ) {
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = BattleMainViewModel.ID_ATTACK,
                        childViewModel = battleMainViewModel,
                    ),
                text = TextData.BATTLE_MAIN_ATTACK,
            )

            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = BattleMainViewModel.ID_ESCAPE,
                        childViewModel = battleMainViewModel,
                    ),
                text = TextData.BATTLE_MAIN_ESCAPE,
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
