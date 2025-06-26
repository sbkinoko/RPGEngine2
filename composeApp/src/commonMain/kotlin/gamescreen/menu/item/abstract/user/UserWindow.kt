package gamescreen.menu.item.abstract.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import gamescreen.menu.item.list.UnSelectableItemList
import gamescreen.menu.item.list.UserList
import values.Colors

@Composable
fun UserWindow(
    itemUserViewModel: ItemUserViewModel<*, *>,
    modifier: Modifier = Modifier,
) {
    val selectedId by itemUserViewModel.selectedFlowState.collectAsState()

    Row(
        modifier = modifier
            .background(
                color = Colors.MenuBackground,
            )
    ) {
        UserList(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            itemUserViewModel = itemUserViewModel,
        )

        UnSelectableItemList(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            selectedUserId = selectedId,
            itemList = itemUserViewModel,
        )
    }
}
