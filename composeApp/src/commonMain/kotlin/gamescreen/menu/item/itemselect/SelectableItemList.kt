package gamescreen.menu.item.itemselect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.menuItem
import common.layout.CenterText
import gamescreen.menu.item.user.ItemUserViewModel

@Composable
fun SelectableItemList(
    selectedUserId: Int,
    itemUserViewModel: ItemUserViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        itemUserViewModel.getPlayerItemListAt(selectedUserId)
            .forEachIndexed { index, itemId ->
                CenterText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .menuItem(
                            id = index,
                            childViewModel = itemUserViewModel,
                        ),
                    text = itemUserViewModel.getItemName(itemId),
                )
            }
    }
}
