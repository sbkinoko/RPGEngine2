package gamescreen.battle.command.playeraction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import core.domain.status.StatusData
import gamescreen.battle.command.CommandMenu
import org.koin.compose.koinInject

@Composable
fun PlayerAction(
    playerStatus: StatusData,
    modifier: Modifier = Modifier,
    playerActionViewModel: PlayerActionViewModel = koinInject(),
) {
    LaunchedEffect(playerActionViewModel.playerId) {
        playerActionViewModel.init()
    }

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = playerStatus.name,
        )

        CommandMenu(
            itemList = playerActionViewModel.entries,
            onClick2 = playerActionViewModel,
            modifier = modifier,
        )
    }
}
