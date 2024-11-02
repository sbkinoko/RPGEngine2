package gamescreen.menu.item.abstract.itemselect

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.equalAllocationModifier
import common.values.Colors
import gamescreen.menu.component.StatusComponent
import gamescreen.menu.item.list.SelectableItemList

@Composable
fun ItemListWindow(
    modifier: Modifier = Modifier,
    itemListViewModel: ItemListViewModel,
) {
    val user = itemListViewModel.userId

    val flg = remember {
        mutableStateOf(true)
    }

    if (flg.value) {
        itemListViewModel.init()
        flg.value = false
    }

    val selected = itemListViewModel.getSelectedAsState().value

    Row(
        modifier = modifier.background(
            color = Colors.MenuBackground,
        )
    ) {
        SelectableItemList(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            selectedUserId = user,
            itemUserViewModel = itemListViewModel,
            itemList = itemListViewModel,
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            Text(
                modifier = equalAllocationModifier,
                text = itemListViewModel.getExplainAt(
                    position = selected,
                )
            )
            StatusComponent(
                modifier = equalAllocationModifier
                    .border(
                        width = 1.dp,
                        color = Colors.StatusComponent,
                        shape = RectangleShape,
                    ),
                statusId = user,
            )
        }
    }
}
