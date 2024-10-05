package gamescreen.menu.item.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.values.Colors

@Composable
fun UserWindow(
    toolUserViewModel: ItemUserViewModel,
    modifier: Modifier = Modifier,
) {
    val selectedId = toolUserViewModel.getSelectedAsState().value
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
            itemUserViewModel = toolUserViewModel,
        )

        UnSelectableItemList(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            selectedUserId = selectedId,
            itemUserViewModel = toolUserViewModel,
        )
    }
}
