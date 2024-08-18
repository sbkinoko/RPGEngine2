package battle.command.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.selectable
import common.layout.CenterText

@Composable
fun MainCommand(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val selected = mainViewModel.getSelectedAsState().value
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            CenterText(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                    .selectable(
                        id = 0,
                        selected = selected,
                    ).clickable {
                        mainViewModel.onClickItem(
                            id = 0,
                        )
                    },
                text = "攻撃",
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .selectable(
                        id = 1,
                        selected = selected,
                    ).clickable {
                        mainViewModel.onClickItem(
                            id = 1,
                        )
                    },
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
