package gamescreen.menu.item.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import common.layout.CenterText
import common.values.playerNum
import gamescreen.menu.item.abstract.user.ItemUserViewModel

@Composable
fun UserList(
    itemUserViewModel: ItemUserViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        for (i in 0 until playerNum) {
            CenterText(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .menuItem(
                        id = i,
                        childViewModel = itemUserViewModel,
                    ),
                text = itemUserViewModel.getPlayerNameAt(i),
            )
        }
    }
}
