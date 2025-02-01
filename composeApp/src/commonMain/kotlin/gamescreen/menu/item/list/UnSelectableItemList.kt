package gamescreen.menu.item.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.selectable
import common.layout.CenterText

@Composable
fun UnSelectableItemList(
    selectedUserId: Int,
    itemList: ItemList<*>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        itemList.getPlayerItemListAt(selectedUserId)
            .forEachIndexed { _, item ->
                CenterText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .selectable(
                            // 選択状態にならないようにしておく
                            id = 1,
                            selected = 2,
                        ),
                    text = item.name,
                )
            }
    }
}
