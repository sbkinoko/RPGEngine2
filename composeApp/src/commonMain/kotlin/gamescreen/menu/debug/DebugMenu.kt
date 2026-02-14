package gamescreen.menu.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import common.layout.CenterText
import org.koin.compose.koinInject
import values.Colors

@Composable
fun DebugMenu(
    modifier: Modifier = Modifier.Companion,
    debugViewModel: DebugViewModel = koinInject(),
) {
    val frameFlg by debugViewModel.frameState.collectAsState()

    val collisionFlg by debugViewModel.collisionState.collectAsState()

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

            // fixme listに修正してforで書きたい
            CenterText(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .menuItem(
                        id = 0,
                        menuItem = debugViewModel,
                    ),
                text = debugViewModel.getText(0) + collisionFlg,
            )

            CenterText(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .menuItem(
                        id = 1,
                        menuItem = debugViewModel,
                    ),
                text = debugViewModel.getText(1) + frameFlg
            )
        }
    }
}
