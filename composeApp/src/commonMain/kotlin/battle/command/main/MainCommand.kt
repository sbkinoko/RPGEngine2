package battle.command.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.layout.CenterText

@Composable
fun MainCommand(
    mainCommandCallBack: MainCommandCallBack,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            CenterText(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        mainCommandCallBack.attack.invoke()
                    },
                text = "攻撃",
            )
            Spacer(
                modifier = Modifier.weight(1f),
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
