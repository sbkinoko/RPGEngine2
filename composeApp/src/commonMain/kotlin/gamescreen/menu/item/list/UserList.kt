package gamescreen.menu.item.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import common.layout.CenterText
import gamescreen.menu.item.abstract.user.ItemUserViewModel

@Composable
fun UserList(
    itemUserViewModel: ItemUserViewModel<*, *>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        for (i in 0 until itemUserViewModel.playerNum) {
            CenterText(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .menuItem(
                        id = i,
                        menuItem = itemUserViewModel,
                    ),
                text = itemUserViewModel.getPlayerNameAt(i),
            )
        }
    }
}
