package gamescreen.menu.item.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.menuItem
import common.layout.CenterText
import core.menu.SelectableChildViewModel

@Composable
fun SelectableItemList(
    selectedUserId: Int,
    itemUserViewModel: SelectableChildViewModel<Int>,
    itemList: ItemList<*>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        itemList.getPlayerItemListAt(selectedUserId)
            .forEachIndexed { index, item ->
                CenterText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .menuItem(
                            id = index,
                            menuItem = itemUserViewModel,
                        ),
                    text = item.name,
                )
            }
    }
}
