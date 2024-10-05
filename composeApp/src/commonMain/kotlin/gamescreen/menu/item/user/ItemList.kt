package gamescreen.menu.item.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.menuItem
import common.extension.selectable
import common.layout.CenterText

@Composable
fun UnSelectableItemList(
    selectedUserId: Int,
    itemUserViewModel: ItemUserViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        itemUserViewModel.getPlayerItemListAt(selectedUserId)
            .forEachIndexed { _, itemId ->
                CenterText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .selectable(
                            // 選択状態にならないようにしておく
                            id = 1,
                            selected = 2,
                        ),
                    text = itemUserViewModel.getItemName(itemId),
                )
            }
    }
}

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
