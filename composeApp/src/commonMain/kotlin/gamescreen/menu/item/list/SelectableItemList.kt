package gamescreen.menu.item.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.menuItem
import common.layout.CenterText
import core.menu.MenuItem

@Composable
fun <T> SelectableItemList(
    selectedUserId: Int,
    menuItem: MenuItem<Int>,
    itemList: ItemList<T>,
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
                            menuItem = menuItem,
                        ),
                    text = item.name,
                )
            }
    }
}
