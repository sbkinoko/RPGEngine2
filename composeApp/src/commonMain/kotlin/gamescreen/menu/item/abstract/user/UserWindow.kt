package gamescreen.menu.item.abstract.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import gamescreen.menu.item.list.SelectableItemList
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

        Box(
            modifier = Modifier.fillMaxHeight()
                .weight(1f)
        ) {
            SelectableItemList(
                modifier = Modifier
                    .fillMaxHeight(),
                selectedUserId = selectedId,
                itemList = itemUserViewModel,
                itemUserViewModel = itemUserViewModel,
            )

            Spacer(
                modifier = Modifier.fillMaxSize()
                    .clickable { })
        }
    }
}
